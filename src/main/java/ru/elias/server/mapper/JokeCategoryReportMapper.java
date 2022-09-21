package ru.elias.server.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.elias.server.config.SpringMapperConfig;
import ru.elias.server.dto.report.JokesByCategoriesReportData;
import ru.elias.server.model.Joke;

@Mapper(config = SpringMapperConfig.class)
public interface JokeCategoryReportMapper {

    @Mapping(target = "categoryName", source = "entity.category.name")
    @Mapping(target = "jokeName", source = "entity.name")
    JokesByCategoriesReportData map(Joke entity);

}
