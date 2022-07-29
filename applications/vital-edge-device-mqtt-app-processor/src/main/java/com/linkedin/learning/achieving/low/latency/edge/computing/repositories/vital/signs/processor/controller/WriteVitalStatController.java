package com.linkedin.learning.achieving.low.latency.edge.computing.repositories.vital.signs.processor.controller;

import com.linkedin.learning.achieving.low.latency.edge.computing.domain.VitalStat;
import com.linkedin.learning.achieving.low.latency.edge.computing.repositories.vital.signs.repositories.VitalStatRepository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * WriteVitalStatController
 *
 * @author Gregory Green
 */
@RestController
public class WriteVitalStatController
{
    private final VitalStatRepository vitalStatRepository;

    public WriteVitalStatController(VitalStatRepository vitalStatRepository)
    {
        this.vitalStatRepository = vitalStatRepository;
    }

    @PostMapping("vitalStats/")
    public void saveVitalStat(@RequestBody VitalStat vitalStat)
    {
        this.vitalStatRepository.save(vitalStat);
    }
}
