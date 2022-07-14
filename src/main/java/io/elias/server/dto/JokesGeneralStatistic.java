package io.elias.server.dto;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Транспортный объект категории")
public class JokesGeneralStatistic {

    @Parameter(description = "Наименование категории")
    private String name;

    @Parameter(description = "Количество шуток в категории")
    private Long jokesCount;

}
