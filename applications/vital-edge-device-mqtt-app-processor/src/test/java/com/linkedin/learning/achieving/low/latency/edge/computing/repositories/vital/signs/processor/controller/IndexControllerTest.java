package com.linkedin.learning.achieving.low.latency.edge.computing.repositories.vital.signs.processor.controller;

import com.linkedin.learning.achieving.low.latency.edge.computing.repositories.vital.stats.repository.VitalRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.ModelMap;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class IndexControllerTest
{
    @Mock
    private VitalRepository vitalRepository;

    @Test
    void viewHome()
    {
        var subject = new IndexController(vitalRepository);
        var  model = new ModelMap();
        subject.viewVitals(model);

        verify(vitalRepository).findAll();
    }
}