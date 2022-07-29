package com.linkedin.learning.achieving.low.latency.edge.computing.repositories.vital.signs.processor.events.sse;

import com.linkedin.learning.achieving.low.latency.edge.computing.domain.VitalOverview;
import com.linkedin.learning.achieving.low.latency.edge.computing.services.vital.VitalDataService;
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
public class VitalOverviewsSseEventsController
{
    private final VitalDataService vitalDataService;

    public VitalOverviewsSseEventsController(VitalDataService vitalDataService)
    {
        this.vitalDataService = vitalDataService;
    }


    /**
     *
     * @return the vital overviews (condition and priorities)
     */
    @GetMapping("/streamVitalOverviewEvents-sse")
    public Flux<ServerSentEvent<Iterable<VitalOverview>>> streamVitalOverviewEvents()
    {
        return Flux.interval(Duration.ofSeconds(1))
                   .map(sequence -> ServerSentEvent.<Iterable<VitalOverview>> builder()
                                                   .id(String.valueOf(sequence))
                                                   .data(vitalDataService.findVitalOverviews())
                                                   .build());
    }
}
