package io.elias.server.filter.base;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Фильтр строковых параметров")
public class StringFilter extends MainFilter<String> {

    @Parameter(description = "Содержит текст")
    private String contains;

    @Parameter(description = "Не содержит текст")
    private String doesntContains;

    @Parameter(description = "Начинается с ")
    private String startWith;

    @Parameter(description = "Оканчивается на ")
    private String endWith;

}
