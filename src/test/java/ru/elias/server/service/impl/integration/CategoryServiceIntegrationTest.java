package ru.elias.server.service.impl.integration;

import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.ExpectedDataSet;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import ru.elias.server.AbstractDbRiderTest;
import ru.elias.server.dto.CategoryDto;
import ru.elias.server.exception.BusinessException;
import ru.elias.server.model.Category;
import ru.elias.server.repository.CategoryRepository;
import ru.elias.server.service.impl.CategoryServiceImpl;

class CategoryServiceIntegrationTest extends AbstractDbRiderTest {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private CategoryServiceImpl categoryService;

    @Test
    @DataSet(value = "data/yml/ethalonData.yml")
    void whenGetCategoryByNameThenReturnCategoryDto() {
        var expected = categoryRepository.findAll().get(0);
        var actual = categoryService.getCategoryByName(expected.getName());
        assertThat(actual.getBody()).isNotNull();
        assertThat(actual.getBody().getName()).isEqualTo(expected.getName());
    }

    @Test
    @DataSet("data/yml/ethalonData.yml")
    void whenGetCategoryByNameThenThrowException() {
        assertThatThrownBy(() -> categoryService.getCategoryByName("asdsdsad"))
                .isInstanceOf(BusinessException.class)
                .hasMessageContaining("Категория с названием asdsdsad не найдена!");
    }

    @Test
    @DataSet("data/yml/ethalonData.yml")
    void whenGetAllCategoriesThenReturnListDtoCategories() {
        var categoryNames = categoryRepository.findAll().stream()
                                           .map(Category::getName)
                                           .toArray(String[]::new);
        var actual = categoryService.getAllCategories();
        assertThat(actual.getBody()).isNotNull();
        assertThat(actual.getBody()).hasSize(3);
        assertThat(actual.getBody())
                .flatExtracting(CategoryDto::getName)
                .containsExactlyInAnyOrder(categoryNames);
    }

    @Test
    @DataSet(value = "data/yml/createCategoryWithManualModeIntegrationTest.yml")
    @ExpectedDataSet(value = "data/yml/createCategoryWithManualModeIntegrationTestExpected.yml",
                     ignoreCols = "created_at")
    void whenCreateCategoryOnManualMode() {
        var dto = dataFactory.categoryDto();
        var actual = categoryService.createCategories(false, dto);
        assertThat(actual.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    }

    @Test
    @DataSet(value = "data/yml/createCategoryWithAutoModeIntegrationTest.yml")
    @ExpectedDataSet(value = "data/yml/createCategoryWithAutoModeIntegrationTestExpected.yml",
                     ignoreCols = "created_at")
    void whenCreateCategoryOnAutoMode() {
        var actual = categoryService.createCategories(true, null);
        assertThat(actual.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    }

}
