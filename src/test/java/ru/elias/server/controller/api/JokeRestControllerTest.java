package ru.elias.server.controller.api;

import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import static org.mockito.Mockito.when;
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
    void whenGetJokeByIdThenReturn200() throws Exception {
        when(jokeService.getJokeById(Mockito.anyLong()))
               .thenReturn(ResponseEntity.ok(JokeDto.builder()
                                                    .joke("joke")
                                                    .category("cat")
                                                    .build())
               );
        performOkRequest(BASE_PATH + "/1");
    }

    @Test
    void whenGetJokeByIdThenReturn404WithJokeNotFoundException() throws Exception {
        when(jokeService.getJokeById(ArgumentMatchers.anyLong()))
               .thenThrow(new BusinessException(ErrorType.JOKE_NOT_FOUND_BY_ID));
        performNotFoundRequest(HttpMethod.GET, BASE_PATH + "/1", null, ErrorType.JOKE_NOT_FOUND_BY_ID);
    }

    @Test
    void whenGetRandomJokeThenReturn200() throws Exception {
        when(jokeService.getRandomJoke())
               .thenReturn(ResponseEntity.ok(JokeDto.builder()
                                                    .joke("joke")
                                                    .category("cat")
                                                    .build())
               );
        performOkRequest(BASE_PATH + "/random");
    }

    @Test
    void getRandomJokeByCategory() throws Exception {
        when(jokeService.getRandomJokeByCategory(ArgumentMatchers.anyString()))
               .thenReturn(ResponseEntity.ok(JokeDto.builder()
                                                    .joke("joke")
                                                    .category("cat")
                                                    .build())
               );
        performOkRequest(BASE_PATH + "/random" + "/cat");
    }

    @Test
    void whenCreateJokeWithAutoModeThenReturn201() throws Exception {
        when(jokeService.createJoke(ArgumentMatchers.anyBoolean(),
                                            ArgumentMatchers.anyString(),
                                            ArgumentMatchers.any()))
               .thenReturn(ResponseEntity.status(HttpStatus.CREATED).build());
        performCreatedRequest(BASE_PATH, "?auto=true&category=some-cat", null);
    }

    @Test
    void whenCreateJokeWithManualModeThenReturn201() throws Exception {
        when(jokeService.createJoke(ArgumentMatchers.anyBoolean(),
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
    void getJokesCountStatistics() throws Exception {
        when(jokeService.getJokesCountStatistics())
               .thenReturn(ResponseEntity.ok(List.of(JokesGeneralStatistic.builder()
                                                                          .name("cat")
                                                                          .jokesCount(123L)
                                                                          .build()))
               );
        performOkRequest(BASE_PATH + "/statistics");
    }

    @Test
    void getJokesByCriteria() throws Exception {
        var criteria = getCriteria();
        when(jokeService.getRandomJokeByCriteria(criteria))
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