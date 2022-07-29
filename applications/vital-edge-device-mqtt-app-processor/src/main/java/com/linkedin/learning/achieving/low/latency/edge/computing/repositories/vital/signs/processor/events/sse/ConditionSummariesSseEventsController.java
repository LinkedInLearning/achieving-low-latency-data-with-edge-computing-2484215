package com.linkedin.learning.achieving.low.latency.edge.computing.repositories.vital.signs.processor.events.sse;

import com.linkedin.learning.achieving.low.latency.edge.computing.domain.aggregates.ConditionSummaries;
import com.linkedin.learning.achieving.low.latency.edge.computing.services.alarms.AlarmDataService;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.time.Duration;

/**
 * VitalSummaryEventsController
 *
 * @author Gregory Green
 */
@RestController
public class ConditionSummariesSseEventsController
{
    private final AlarmDataService alarmDataService;

    public ConditionSummariesSseEventsController(AlarmDataService alarmDataService)
    {
        this.alarmDataService = alarmDataService;
    }

    @GetMapping("/vitalSummaryEvents-sse")
    public Flux<ServerSentEvent<ConditionSummaries>> streamConditionCountEvents() {
        return Flux.interval(Duration.ofSeconds(1))
                   .map(sequence -> ServerSentEvent.<ConditionSummaries> builder()
                                                   .id(String.valueOf(sequence))
                                                   .data(alarmDataService.calculateConditionCounts())
                                                   .build());
    }

}
