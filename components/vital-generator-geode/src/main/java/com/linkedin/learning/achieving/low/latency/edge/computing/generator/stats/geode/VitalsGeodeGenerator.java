package com.linkedin.learning.achieving.low.latency.edge.computing.generator.stats.geode;

import com.linkedin.learning.achieving.low.latency.edge.computing.domain.Vital;
import com.linkedin.learning.achieving.low.latency.edge.computing.domain.VitalStat;
import com.linkedin.learning.achieving.low.latency.edge.computing.domain.VitalStatName;
import com.linkedin.learning.achieving.low.latency.edge.computing.generator.stats.VitalCreator;
import com.linkedin.learning.achieving.low.latency.edge.computing.repositories.vital.stats.repository.VitalRepository;
import nyla.solutions.core.util.Digits;
import nyla.solutions.core.util.Text;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

/**
 * VitalStatsGenerator to data simulation generation
 *
 * @author Gregory Green
 */
@Component
public class VitalsGeodeGenerator
{
    private final VitalRepository vitalRepository;
    private final Consumer<VitalStat> vitalStatConsumer;
    private final Environment environment;
    private final VitalCreator vitalCreator;
    private final int minVitalId;
    private final int maxVitalId;

    public VitalsGeodeGenerator(VitalRepository vitalRepository,
                                Consumer<VitalStat> vitalStatRepository,
                                VitalCreator vitalCreator,
                                Environment environment,
                                @Value("${vitals.minVitalId}")
                                        int minVitalId,
                                @Value("${vitals.maxVitalId}")
                                        int maxVitalId)
    {
        this.vitalRepository = vitalRepository;
        this.vitalStatConsumer = vitalStatRepository;
        this.vitalCreator = vitalCreator;
        this.environment = environment;
        this.minVitalId = minVitalId;
        this.maxVitalId = maxVitalId;
    }

    public void generateStatForVitalId(String vitalId,String statName )
    {
        VitalStat vitalStat = createVitalStat(vitalId, statName);

        this.vitalStatConsumer.accept(vitalStat);
    }

    protected VitalStat createVitalStat(String vitalId, String statName)
    {
        LocalDateTime now = LocalDateTime.now();
        String label = Text.formatDate(now,"HH:mm:ss");

        var minProp = Text.merge(".","vital", statName,"min");
        Double min = environment.getRequiredProperty(minProp,Double.class);

        var maxProp = Text.merge(".","vital", statName,"max");
        Double max = environment.getRequiredProperty(maxProp,Double.class);


        double value = 0;

        if(min != null && max != null)
            value = new Digits().generateDouble(min,max);

        var id = Text.merge("|", vitalId, statName);

        var vitalStat = new VitalStat();
        vitalStat.setStatName(statName);
        vitalStat.setLabel(label);
        vitalStat.setTimestamp(now);
        vitalStat.setId(id);
        vitalStat.setValue(value);
        vitalStat.setVitalId(vitalId);

        System.out.println("vital:"+vitalStat);
        return vitalStat;
    }



    public void generateStatsForAllVitals()
    {
        var vitals= vitalRepository.findAll();

        for (Vital v:vitals) {
            var statNames = v.getStats();

            for (VitalStatName vitalStateName:statNames) {
                generateStatForVitalId(v.getId(),vitalStateName.getStatName());
            }
        }
    }


    /**
     *
     * @param count the number of vitals to create
     */
    public List<Vital> generateVital(int count)
    {
        var id = "";

        var vitals = new ArrayList<Vital>(count);

        Vital vital = null;


        Optional<Vital> optional;
        int vitalId = minVitalId;
        for (int i = 1; i <= count; i++) {
            id = String.valueOf(vitalId);

            vitalId = (vitalId+1)%(maxVitalId+1);

            if(vitalId == 0)
                vitalId = minVitalId;

            optional = this.vitalRepository.findById(id);
            if(optional.isPresent())
            {
                vitals.add(optional.get());
                continue;
            }

            vitalCreator.setId(id);
            vital = vitalCreator.create();

            this.vitalRepository.save(vital);

            vitals.add(vital);
        }

        return vitals;
    }
}
