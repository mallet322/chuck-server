package ru.elias.server.service;

import javax.annotation.PostConstruct;

import java.util.stream.Collectors;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.MultiGauge;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.elias.server.dto.StatisticGauge;
import ru.elias.server.mapper.CategoryMapper;
import ru.elias.server.repository.JokeQueryCustomRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class Job {

    private final JokeQueryCustomRepository jokeQueryCustomRepository;

    private final CategoryMapper mapper;

    private final MeterRegistry meterRegistry;

    private MultiGauge multiGauge;

    @PostConstruct
    protected void init() {
        multiGauge = MultiGauge.builder("jokes_grouped_by_category_metric")
                               .register(meterRegistry);
    }

    @Scheduled(fixedDelay = 3000)
    public void updateStatisticGauge() {
        multiGauge.register(jokeQueryCustomRepository.countByCategories()
                                                     .stream()
                                                     .map(mapper::map)
                                                     .map(StatisticGauge::toRow)
                                                     .collect(Collectors.toList()), true);
    }

}
