package io.elias.server.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

import io.elias.server.config.SpringMapperConfig;
import io.elias.server.dto.JokeDto;
import io.elias.server.exception.BusinessException;
import io.elias.server.exception.ErrorType;
import io.elias.server.model.Category;
import io.elias.server.model.Joke;
import io.elias.server.repository.CategoryRepository;
import io.elias.server.service.MessageSourceHelper;

@Mapper(config = SpringMapperConfig.class)
public abstract class JokeMapper {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private MessageSourceHelper messageSourceHelper;

    @Mapping(source = "dto.joke", target = "value")
    @Mapping(source = "dto.category", target = "category", qualifiedByName = "mapCategory")
    public abstract Joke map(JokeDto dto);

    @Mapping(target = "joke", source = "entity.value")
    @Mapping(target = "category", source = "entity.category.name")
    public abstract JokeDto map(Joke entity);

    @Named("mapCategory")
    public Category mapCategory(String categoryName) {
        return categoryRepository.findCategoryByName(categoryName)
                  .orElseThrow(() -> {
                     var errorType = ErrorType.CATEGORY_NOT_FOUND_BY_NAME;
                     var msg = messageSourceHelper.getMessage(errorType, categoryName);
                     throw new BusinessException(errorType, msg);
                  });
    }

}
