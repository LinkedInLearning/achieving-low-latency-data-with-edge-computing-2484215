package com.linkedin.learning.achieving.low.latency.edge.computing.repositories.vital.signs.processor;

import nyla.solutions.core.patterns.expression.ComparableExpression;
import nyla.solutions.core.patterns.expression.OrExpression;
import nyla.solutions.core.patterns.expression.bre.BusinessRuleEngine;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * BusinessRulesAbnormalVitalConfig
 *
 * @author Gregory Green
 */
@Configuration
public class BusinessRulesAbnormalVitalConfig
{
    @Bean
    public BusinessRuleEngine<Double,Boolean> bre()
    {
        return BusinessRuleEngine
                .builder()
                .rule("heartRate",
                        new OrExpression<Double>(ComparableExpression.lessThan(55.00),
                                ComparableExpression.greaterThan(105.00)))
                .rule("bodyTemperature",new OrExpression<Double>(
                        ComparableExpression.lessThan(95.00),
                        ComparableExpression.greaterThan(103.00)))
                .rule( "respirationRate",new OrExpression<Double>(
                        ComparableExpression.lessThan(12.00),
                        ComparableExpression.greaterThan(16.00)))
                .rule("bloodPressureDiastolic", ComparableExpression.lessThan(80.00))
                .rule("bloodPressureSystolic", ComparableExpression.greaterThan(130.00))
                .build();
    }
}
