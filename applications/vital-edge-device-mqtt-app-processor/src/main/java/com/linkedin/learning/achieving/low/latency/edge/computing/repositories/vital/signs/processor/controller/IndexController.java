package com.linkedin.learning.achieving.low.latency.edge.computing.repositories.vital.signs.processor.controller;

import com.linkedin.learning.achieving.low.latency.edge.computing.domain.Vital;
import com.linkedin.learning.achieving.low.latency.edge.computing.repositories.vital.stats.repository.VitalRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

/**
 * Controller for home page
 * @author Gregory Green
 */
@Controller
public class IndexController
{
    private final VitalRepository vitalRepository;

    /**
     * Creator
     * @param vitalRepository the vital repository
     */
    public IndexController(VitalRepository vitalRepository)
    {
        this.vitalRepository = vitalRepository;
    }


    @RequestMapping("/")
    public String viewVitals(@ModelAttribute("model") ModelMap model)
    {
        var vitals = vitalRepository.findAll();
        List<Vital> result = new ArrayList<Vital>();

        for (Vital v:vitals) {
            result.add(v);
        }

        model.addAttribute("vitalSigns", result);

        return "vitalSigns";
    }
}
