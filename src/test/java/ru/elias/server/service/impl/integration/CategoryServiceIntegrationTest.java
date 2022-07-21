package ru.elias.server.service.impl.integration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import ru.elias.server.CategoryDataFactory;
import ru.elias.server.TestContainersAbstractTestCase;
import ru.elias.server.client.JokeClient;
import ru.elias.server.dto.CategoryDto;
import ru.elias.server.exception.BusinessException;
import ru.elias.server.model.Category;
import ru.elias.server.repository.CategoryRepository;
import ru.elias.server.service.impl.CategoryServiceImpl;

class CategoryServiceIntegrationTest extends TestContainersAbstractTestCase {

    @Autowired
    private CategoryDataFactory categoryDataFactory;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private JokeClient jokeClient;

    @Autowired
    private CategoryServiceImpl categoryService;

    @Test
    void whenGetCategoryByNameThenReturnCategoryDto() {
        var entity = categoryDataFactory.category();
        categoryRepository.save(entity);
        var actual = categoryService.getCategoryByName(entity.getName());
        assertThat(actual.getBody()).isNotNull();
        assertThat(actual.getBody().getName()).isEqualTo(entity.getName());
    }

    @Test
    void whenGetCategoryByNameThenThrowException() {
        var entity = categoryDataFactory.category();
        categoryRepository.save(entity);
        assertThatThrownBy(() -> categoryService.getCategoryByName("asdsdsad"))
                .isInstanceOf(BusinessException.class)
                .hasMessageContaining("Категория с названием asdsdsad не найдена!");
    }

    @Test
    void whenGetAllCategoriesThenReturnListDtoCategories() {
        var entity = categoryDataFactory.category();
        categoryRepository.save(entity);
        var actual = categoryService.getAllCategories();
        assertThat(actual.getBody()).isNotNull();
        assertThat(actual.getBody()).hasSize(1);
        assertThat(actual.getBody())
                .flatExtracting(CategoryDto::getName)
                .containsExactlyInAnyOrder(entity.getName());
    }

    @Test
    void whenCreateCategoryOnManualMode() {
        var dto = categoryDataFactory.categoryDto();
        var actual = categoryService.createCategories(false, dto);
        assertThat(actual.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    }

    @Test
    void whenCreateCategoryOnAutoMode() {
        var actual = categoryService.createCategories(true, null);
        var expectedCategories = jokeClient.getAllCategories().toArray(String[]::new);
        var actualCategories = categoryRepository.findAll();
        assertThat(actual.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(actualCategories).isNotNull();
        assertThat(actualCategories).hasSize(16);
        assertThat(actualCategories)
                .flatExtracting(Category::getName)
                .containsExactlyInAnyOrder(expectedCategories);

    }

}
