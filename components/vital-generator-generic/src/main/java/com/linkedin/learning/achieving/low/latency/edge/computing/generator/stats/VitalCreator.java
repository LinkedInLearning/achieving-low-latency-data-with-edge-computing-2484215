package com.linkedin.learning.achieving.low.latency.edge.computing.generator.stats;

import com.linkedin.learning.achieving.low.latency.edge.computing.domain.Vital;
import com.linkedin.learning.achieving.low.latency.edge.computing.domain.VitalStatName;
import nyla.solutions.core.patterns.creational.Creator;
import nyla.solutions.core.patterns.creational.generator.FixedNameCreator;
import nyla.solutions.core.util.Digits;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * VitalCreator implement creates Vital instance with random data.
 *
 * @author Gregory Green
 */
@Component
public class VitalCreator implements Creator<Vital>
{
    private final FixedNameCreator fullNameCreator;
    private final String urlPrefix;
    private final VitalStatName[] stats;
    private final int minVitalId;
    private final int maxVitalId;

    @Value("${vitals.vitalSigns.urlSuffix:.png}")
    private String urlSuffix = ".png";
    private Digits digits = new Digits();
    private final int minLocationCode;
    private final int maxLocationCode;
    private String vitalId = null;

    public VitalCreator(
            FixedNameCreator fullNameCreator, @Value("${vitals.minVitalId}") int minVitalId,
            @Value("${vitals.maxVitalId}") int maxVitalId,
            @Value("${vitals.urlPrefix}") String urlPrefix,
            VitalStatName[] stats,
            @Value("${vitals.minLocationCode}") int minLocationCode,
            @Value("${vitals.maxLocationCode}") int maxLocationCode)
    {
        this.fullNameCreator = fullNameCreator;
        this.urlPrefix = urlPrefix;
        this.maxVitalId = maxVitalId;
        this.minVitalId = minVitalId;
        this.stats = stats;
        this.minLocationCode = minLocationCode;
        this.maxLocationCode = maxLocationCode;
    }

    @Override
    public Vital create()
    {
        var vital = new Vital();


        if(vitalId == null)
        {
            vital.setId(String.valueOf(digits.generateInteger(minVitalId,maxVitalId)));
        }
        else
        {
            vital.setId(vitalId);
        }
        vital.setName(fullNameCreator.create());
        vital.setStats(stats);
        vital.setUrl(new StringBuilder().append(urlPrefix).append(vital.getId()).append(urlSuffix).toString());
        vital.setLocation(String.valueOf(digits.generateInteger(minLocationCode,maxLocationCode)));
        return vital;
    }

    public void setId(String vitalId)
    {
        this.vitalId = vitalId;

    }
}
