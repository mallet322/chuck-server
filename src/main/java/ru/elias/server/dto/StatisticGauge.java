package ru.elias.server.dto;

import io.micrometer.core.instrument.MultiGauge;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder(toBuilder = true)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StatisticGauge {

    private String category;

    private Long jokeCounter;

    public MultiGauge.Row

}
