package ru.elias.server.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

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
public class CategoryDto {

    @Parameter(description = "Наименование категории")
    @NotBlank//(message = "Наименование категории не должно быть пыстым!")
    @NotNull//(message = "Наименование категории не должно быть равным нулю!")
    private String name;

}
