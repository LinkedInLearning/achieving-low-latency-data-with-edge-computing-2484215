package com.linkedin.learning.achieving.low.latency.edge.computing.repositories.vital.signs.rules;

import com.linkedin.learning.achieving.low.latency.edge.computing.domain.VitalStat;
import nyla.solutions.core.patterns.expression.bre.BusinessRuleEngine;
import org.springframework.stereotype.Service;

/**
 * VitalRuleService
 *
 * @author Gregory Green
 */
@Service
public class VitalRuleService
{
    private final BusinessRuleEngine<Double,Boolean> businessRuleEngine;

    public VitalRuleService(BusinessRuleEngine<Double,Boolean>  businessRuleEngine)
    {
        this.businessRuleEngine = businessRuleEngine;
    }

    public boolean isAbnormal(VitalStat vitalStat)
    {
        String ruleName =  vitalStat.getStatName();

        Boolean results = this.businessRuleEngine.applyForRule(ruleName,vitalStat.getValue());

        if(results == null)
            throw new IllegalArgumentException("Unable to find rule for "+ruleName);

        return results;
    }
}
