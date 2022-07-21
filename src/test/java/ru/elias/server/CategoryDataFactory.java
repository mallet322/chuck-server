package ru.elias.server;

import com.fasterxml.jackson.core.type.TypeReference;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import ru.elias.server.dto.CategoryDto;
import ru.elias.server.model.Category;

@Component
public class CategoryDataFactory extends AbstractTestDataFactory {

    private static final String CATEGORY_ENTITY_REFERENCE = "/data/category-entity.json";

    private static final String CATEGORY_DTO_REFERENCE = "/data/category-dto.json";

    @SneakyThrows
    public Category category() {
        return getResource(CATEGORY_ENTITY_REFERENCE, new TypeReference<>() {
        });
    }

    @SneakyThrows
    public CategoryDto categoryDto() {
        return getResource(CATEGORY_DTO_REFERENCE, new TypeReference<>() {
        });
    }

}
