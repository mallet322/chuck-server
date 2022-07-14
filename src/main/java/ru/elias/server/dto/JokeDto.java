package ru.elias.server.dto;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Транспортный объект шутки")
public class JokeDto {

    @Parameter(description = "Шутка")
    private String joke;

    @Parameter(description = "Наименование категории")
    private String category;

}
