package ru.elias.server.controller.api;

import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import ru.elias.server.dto.CategoryDto;
import ru.elias.server.exception.BusinessException;
import ru.elias.server.exception.ErrorType;
import ru.elias.server.service.CategoryService;

@DisplayName("Тестирование REST API для работы с категориями")
@AutoConfigureMockMvc
@WebMvcTest(CategoryRestController.class)
class CategoryRestControllerTest extends BaseControllerTest {

    private static final String BASE_PATH = "/api/v1/categories";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CategoryService categoryService;

    @BeforeEach
    public void setUp() {
        super.setUp(mockMvc, objectMapper);
    }

    @Test
    @DisplayName("Получение категории по наименованию. Ожидаемый результат - 200 ОК.")
    void whenGetCategoryByNameThenReturn200() throws Exception {
        Mockito.when(categoryService.getCategoryByName(ArgumentMatchers.anyString()))
                .thenReturn(ResponseEntity.ok(CategoryDto.builder().name("some-category").build()));
        performOkRequest(BASE_PATH + "/some-category");
    }

    @Test
    @DisplayName("Получение категории по наименованию. Ожидаемый результат - 404 Категория не найдена.")
    void whenGetCategoryByNameThenReturn404WithCategoryNotFoundException() throws Exception {
        Mockito.when(categoryService.getCategoryByName(ArgumentMatchers.anyString()))
                .thenThrow(new BusinessException(ErrorType.CATEGORY_NOT_FOUND_BY_NAME));
        performNotFoundRequest(HttpMethod.GET,
                               BASE_PATH + "/some-category",
                               null,
                               ErrorType.CATEGORY_NOT_FOUND_BY_NAME);
    }

    @Test
    @DisplayName("Получение всех категории. Ожидаемый результат - 200 ОК.")
    void whenGetAllCategoriesThenReturn200() throws Exception {
        Mockito.when(categoryService.getAllCategories())
               .thenReturn(ResponseEntity.ok(List.of(CategoryDto.builder().name("some-category").build())));
        performOkRequest(BASE_PATH);
    }

    @Test
    @DisplayName("Создание категории в автоматическом режиме должно возвращать 201 Created")
    void whenCreateCategoryWithAutoModeThenReturn201() throws Exception {
        Mockito.when(categoryService.createCategories(ArgumentMatchers.anyBoolean(),
                                                      ArgumentMatchers.any()))
               .thenReturn(ResponseEntity.status(HttpStatus.CREATED).build());
        performCreatedRequest(BASE_PATH, "?auto=true", null);
    }

    @Test
    @DisplayName("Создание категории в ручном режиме должно возвращать 201 Created")
    void whenCreateCategoryWithManualModeThenReturn201() throws Exception {
        Mockito.when(categoryService.createCategories(ArgumentMatchers.anyBoolean(),
                                                      ArgumentMatchers.any()))
               .thenReturn(ResponseEntity.status(HttpStatus.CREATED).build());
        performCreatedRequest(BASE_PATH, "?auto=false", CategoryDto.builder()
                                                                   .name("some-category")
                                                                   .build());
    }

}