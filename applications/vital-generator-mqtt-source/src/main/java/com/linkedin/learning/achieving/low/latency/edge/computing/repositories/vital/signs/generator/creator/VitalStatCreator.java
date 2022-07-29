package com.linkedin.learning.achieving.low.latency.edge.computing.repositories.vital.signs.generator.creator;

import com.linkedin.learning.achieving.low.latency.edge.computing.domain.VitalStat;
import com.linkedin.learning.achieving.low.latency.edge.computing.domain.VitalStatName;
import nyla.solutions.core.patterns.creational.Creator;
import nyla.solutions.core.util.Digits;
import nyla.solutions.core.util.Text;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * VitalStatCreator
 *
 * @author Gregory Green
 */
@Component
public class VitalStatCreator implements Creator<VitalStat>
{
    private final int minVitalId;
    private final int maxVitalId;
    private final VitalStatName[] statNames;
    private int i = -1;
    private final Digits digital = new Digits();

    public VitalStatCreator(@Value("${vitals.minVitalId}")int minVitalId,@Value("${vitals.maxVitalId}")int maxVitalId, VitalStatName... statNames)
    {
        this.minVitalId = minVitalId;
        this.maxVitalId = maxVitalId;
        this.statNames = statNames;
    }

    @Override
    public VitalStat create()
    {
        i = (i +1) % statNames.length;
        return createVitalStat(statNames[i]);
    }

    protected VitalStat createVitalStat(VitalStatName statName)
    {
        LocalDateTime now = LocalDateTime.now();
        String label = Text.formatDate(now,"HH:mm:ss");

        Double min = statName.getMin();

        Double max = statName.getMax();

        double value = 0;

        if(min != null && max != null)
            value = new Digits().generateDouble(min,max);

        String vitalId = String.valueOf(digital.generateInteger(minVitalId,maxVitalId));
        var id = Text.merge("|", vitalId, statName);

        var vitalStat = new VitalStat();
        vitalStat.setStatName(statName.getStatName());
        vitalStat.setLabel(label);
        vitalStat.setTimestamp(now);
        vitalStat.setId(id);
        vitalStat.setValue(value);
        vitalStat.setVitalId(vitalId);

        return vitalStat;
    }
}
