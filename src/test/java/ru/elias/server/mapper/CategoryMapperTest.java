package ru.elias.server.mapper;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.elias.server.AbstractSpringTest;
import ru.elias.server.TestDataFactory;

class CategoryMapperTest extends AbstractSpringTest {

    @Autowired
    private CategoryMapper mapper;

    @Test
    void whenMapDtoToCategory() {
        var dto = dataFactory.categoryDto();
        var actual = mapper.map(dto);
        assertThat(actual).isNotNull();
        assertThat(actual.getName()).isEqualTo(dto.getName());
    }

    @Test
    void whenMapCategoryToDto() {
        var category = dataFactory.category();
        var actual = mapper.map(category);
        assertThat(actual).isNotNull();
        assertThat(actual.getName()).isEqualTo(category.getName());
    }

    @Test
    void whenMapCategoryNameToCategoryEntity() {
        var categoryName = "some-category";
        var actual = mapper.map(categoryName);
        assertThat(actual).isNotNull();
        assertThat(actual.getName()).isEqualTo(categoryName);
    }

}