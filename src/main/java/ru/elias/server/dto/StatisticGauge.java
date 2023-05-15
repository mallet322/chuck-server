package ru.elias.server.dto;

import io.micrometer.core.instrument.MultiGauge;
import io.micrometer.core.instrument.Tags;
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

    private Long jokesCounter;

    public MultiGauge.Row<StatisticGauge> toRow() {
        return MultiGauge.Row.of(Tags.of("category", category), this, c -> c.jokesCounter);
    }

}
