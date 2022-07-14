package ru.elias.server.filter.base;

import java.util.List;

import io.swagger.v3.oas.annotations.Parameter;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MainFilter<T> {

    @Parameter(description = "полностью совпадает с")
    private T equal;

    @Parameter(description = "полностью не совпадает")
    private T notEqual;

    @Parameter(description = "значение ячейки пустое")
    private Boolean empty;

    @Parameter(description = "значение ячейки не пустое")
    private Boolean nonEmpty;

    @Parameter(description = "совпадает со списком значений")
    private List<T> in;

}
