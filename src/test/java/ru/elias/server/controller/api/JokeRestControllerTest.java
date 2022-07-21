package ru.elias.server.controller.api;

import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.xml.bind.v2.TODO;
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
import ru.elias.server.dto.JokeDto;
import ru.elias.server.dto.JokesGeneralStatistic;
import ru.elias.server.exception.BusinessException;
import ru.elias.server.exception.ErrorType;
import ru.elias.server.filter.JokeQueryCriteria;
import ru.elias.server.filter.base.StringFilter;
import ru.elias.server.service.JokeService;

@DisplayName("Тестирование REST API для работы с шутками")
@AutoConfigureMockMvc
@WebMvcTest(JokeRestController.class)
class JokeRestControllerTest extends BaseControllerTest {

    private static final String BASE_PATH = "/api/v1/jokes";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private JokeService jokeService;

    @BeforeEach
    public void setUp() {
        super.setUp(mockMvc, objectMapper);
    }

    @Test
    @DisplayName("Получение шутки по идентификатору. Ожидаемый результат - 200 ОК.")
    void whenGetJokeByIdThenReturn200() throws Exception {
        Mockito.when(jokeService.getJokeById(Mockito.anyLong()))
               .thenReturn(ResponseEntity.ok(JokeDto.builder()
                                                    .joke("joke")
                                                    .category("cat")
                                                    .build())
               );
        performOkRequest(BASE_PATH + "/1");
    }

    @Test
    @DisplayName("Получение шутки по идентификатору. Ожидаемый результат - 404 Шутка не найдена.")
    void whenGetJokeByIdThenReturn404WithJokeNotFoundException() throws Exception {
        Mockito.when(jokeService.getJokeById(ArgumentMatchers.anyLong()))
               .thenThrow(new BusinessException(ErrorType.JOKE_NOT_FOUND_BY_ID));
        performNotFoundRequest(HttpMethod.GET, BASE_PATH + "/1", null, ErrorType.JOKE_NOT_FOUND_BY_ID);
    }

    @Test
    @DisplayName("Получение случайной шутки. Ожидаемый результат - 200 ОК")
    void whenGetRandomJokeThenReturn200() throws Exception {
        Mockito.when(jokeService.getRandomJoke())
               .thenReturn(ResponseEntity.ok(JokeDto.builder()
                                                    .joke("joke")
                                                    .category("cat")
                                                    .build())
               );
        performOkRequest(BASE_PATH + "/random");
    }

    @Test
    @DisplayName("Получение случайной шутки по наименованию категории. Ожидаемый результат - 200 ОК")
    void getRandomJokeByCategory() throws Exception {
        Mockito.when(jokeService.getRandomJokeByCategory(ArgumentMatchers.anyString()))
               .thenReturn(ResponseEntity.ok(JokeDto.builder()
                                                    .joke("joke")
                                                    .category("cat")
                                                    .build())
               );
        performOkRequest(BASE_PATH + "/random" + "/cat");
    }

    @Test
    @DisplayName("Создание шутки в автоматическом режиме. Ожидаемый результат - 201 Created")
    void whenCreateJokeWithAutoModeThenReturn201() throws Exception {
        Mockito.when(jokeService.createJoke(ArgumentMatchers.anyBoolean(),
                                            ArgumentMatchers.anyString(),
                                            ArgumentMatchers.any()))
               .thenReturn(ResponseEntity.status(HttpStatus.CREATED).build());
        performCreatedRequest(BASE_PATH, "?auto=true&category=some-cat", null);
    }

    @Test
    @DisplayName("Создание шутки в ручном режиме. Ожидаемый результат - 201 Created")
    void whenCreateJokeWithManualModeThenReturn201() throws Exception {
        Mockito.when(jokeService.createJoke(ArgumentMatchers.anyBoolean(),
                                            ArgumentMatchers.anyString(),
                                            ArgumentMatchers.any()))
               .thenReturn(ResponseEntity.status(HttpStatus.CREATED).build());
        performCreatedRequest(BASE_PATH,
                              "?auto=false&category=some-cat",
                              JokeDto.builder()
                                     .joke("joke")
                                     .category("cat")
                                     .build());
    }

    @Test
    @DisplayName("Получение статистики шуток по категориям. Ожидаемый результат - 200 ОК")
    void getJokesCountStatistics() throws Exception {
        Mockito.when(jokeService.getJokesCountStatistics())
               .thenReturn(ResponseEntity.ok(List.of(JokesGeneralStatistic.builder()
                                                                          .name("cat")
                                                                          .jokesCount(123L)
                                                                          .build()))
               );
        performOkRequest(BASE_PATH + "/statistics");
    }

    @Test
    @DisplayName("Получение шутки по критерии. Ожидаемый результат - 200 ОК")
    void getJokesByCriteria() throws Exception {
        var criteria = getCriteria();
        Mockito.when(jokeService.getRandomJokeByCriteria(criteria))
               .thenReturn(ResponseEntity.ok(List.of(JokeDto.builder()
                                                            .joke("joke")
                                                            .category("cat")
                                                            .build()))
               );
        performOkRequest(HttpMethod.GET, BASE_PATH + "/query", criteria);
    }

    private JokeQueryCriteria getCriteria() {
        var criteria = new JokeQueryCriteria();
        criteria.setJokeName(getFilter());
        criteria.setCategoryName(getFilter());
        return criteria;
    }

    private StringFilter getFilter() {
        var filter = new StringFilter();
        filter.setContains("Chuck");
        filter.setNonEmpty(true);
        filter.setEndWith("Norris");
        return filter;
    }
}