package ru.elias.server.mapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;
import ru.elias.server.config.SpringMapperConfig;
import ru.elias.server.dto.JokeDto;
import ru.elias.server.exception.BusinessException;
import ru.elias.server.exception.ErrorType;
import ru.elias.server.model.Category;
import ru.elias.server.model.Joke;
import ru.elias.server.repository.CategoryRepository;
import ru.elias.server.service.MessageSourceHelper;

@Mapper(config = SpringMapperConfig.class)
public abstract class JokeMapper {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private MessageSourceHelper messageSourceHelper;

    @Autowired
    private ObjectMapper objectMapper;

    @Mapping(source = "dto.joke", target = "name")
    @Mapping(source = "dto.category", target = "category", qualifiedByName = "mapCategory")
    public abstract Joke map(JokeDto dto);

    @Mapping(target = "joke", source = "entity.name")
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
