package io.elias.server.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import io.elias.server.config.SpringMapperConfig;
import io.elias.server.dto.CategoryDto;
import io.elias.server.model.Category;

@Mapper(config = SpringMapperConfig.class)
public interface CategoryMapper {

   @Mapping(source = "request.name", target = "name")
   Category map(CategoryDto request);

   @Mapping(source = "entity.name", target = "name")
   CategoryDto map(Category entity);

}
