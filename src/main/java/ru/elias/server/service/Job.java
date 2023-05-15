package ru.elias.server.service;

import io.micrometer.core.instrument.MultiGauge;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.elias.server.repository.JokeQueryCustomRepository;

@Service
@RequiredArgsConstructor
public class Job {

    private final MultiGauge multiGauge;

    private final JokeQueryCustomRepository jokeQueryCustomRepository;

    @Scheduled(fixedDelay = 3000)
    public void updateStatisticGauge() {

        multiGauge.register( true);
    }

}
