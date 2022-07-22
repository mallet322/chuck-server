package ru.elias.server.service.impl.integration;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import org.junit.Ignore;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import ru.elias.server.CategoryDataFactory;
import ru.elias.server.JokeDataFactory;
import ru.elias.server.TestContainersAbstractTestCase;
import ru.elias.server.client.JokeClient;
import ru.elias.server.dto.JokeDto;
import ru.elias.server.dto.JokesGeneralStatistic;
import ru.elias.server.exception.BusinessException;
import ru.elias.server.filter.JokeQueryCriteria;
import ru.elias.server.filter.base.StringFilter;
import ru.elias.server.model.Category;
import ru.elias.server.repository.CategoryRepository;
import ru.elias.server.repository.JokeQueryCustomRepository;
import ru.elias.server.repository.JokeRepository;
import ru.elias.server.service.impl.JokeServiceImpl;

class JokeServiceIntegrationTest extends TestContainersAbstractTestCase {

    @Autowired
    private JokeDataFactory jokeDataFactory;

    @Autowired
    private CategoryDataFactory categoryDataFactory;

    @Autowired
    private JokeRepository jokeRepository;

    @Autowired
    private JokeQueryCustomRepository jokeQueryCustomRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private JokeClient jokeClient;

    @Autowired
    private JokeServiceImpl jokeService;

    @BeforeEach
    public void setUp() {
        var categories = categoryDataFactory.categories();
        categoryRepository.deleteAll();
        categoryRepository.saveAll(categories);
    }

    @Test
    void whenCreateJokeWithAutoMode() {
        var result = jokeService.createJoke(true, "dev", null);
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(jokeRepository.findById(1L))
                .isPresent()
                .get()
                .satisfies(actual -> {
                    assertThat(actual).isNotNull();
                    assertThat(actual.getValue()).isNotBlank();
                    assertThat(actual.getCategory().getName()).isEqualTo("dev");
                });
    }

    @Test
    void whenCreateJokeWithAutoModeThenNotCreateJokeAndThrowBusinessExeption() {
        assertThatThrownBy(() -> jokeService.createJoke(true, "some-category", null))
                .isInstanceOf(BusinessException.class)
                .hasMessageContaining("Категория с названием some-category не найдена!");
    }

    @Test
    void whenCreateJokeWithManualMode() {
        var dto = jokeDataFactory.jokeDto();
        dto.setCategory("dev");
        var result = jokeService.createJoke(false, null, dto);
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    }

    @Test
    void whenCreateJokeWithManualModeThenNotCreateJokeAndThrowBusinessExeption() {
        var dto = jokeDataFactory.jokeDto();
        assertThatThrownBy(() -> jokeService.createJoke(false, null, dto))
                .isInstanceOf(BusinessException.class)
                .hasMessageContaining("Категория с названием some-category не найдена!");
    }

    @Test
    void whenGetRandomJokeThenReturnJokeDto() {
        var joke = jokeDataFactory.joke();
        jokeRepository.save(joke);
        assertThat(jokeService.getRandomJoke().getBody())
                .satisfies(actual -> {
                    assertThat(actual).isNotNull();
                    assertThat(actual.getJoke()).isNotBlank();
                });
    }

    @Test
    void whenGetRandomJokeByCategoryThenReturnJokeDto() {
        var category = categoryDataFactory.category();
        category.setName("dev");
        var joke = jokeDataFactory.joke();
        joke.setCategory(category);
        jokeRepository.save(joke);
        assertThat(jokeService.getRandomJokeByCategory(category.getName()).getBody())
                .satisfies(actual -> {
                    assertThat(actual).isNotNull();
                    assertThat(actual.getJoke()).isNotBlank();
                    assertThat(actual.getCategory()).isEqualTo(category.getName());
                });
    }

    @Test
    void whenGetJokesCountStatisticsThenReturnDtoWithStatistic() {
        var jokes = jokeDataFactory.jokes();
        var categories = categoryRepository.findAll();
        categories.get(0).addJoke(jokes.get(0));
        categories.get(1).addJoke(jokes.get(1));
        categories.get(2).addJoke(jokes.get(2));
        jokeRepository.saveAll(jokes);
        var expectedCategoryNames = categories.stream()
                                              .map(Category::getName)
                                              .toArray(String[]::new);
        assertThat(jokeService.getJokesCountStatistics().getBody())
                .satisfies(stat -> {
                    assertThat(stat).isNotNull();
                    assertThat(stat).hasSize(3);
                    assertThat(stat)
                            .filteredOn(s -> s.getJokesCount() == 1)
                            .hasSize(3);
                    assertThat(stat)
                            .flatExtracting(JokesGeneralStatistic::getName)
                            .containsExactlyInAnyOrder(expectedCategoryNames);
                });
    }

    @Test
    void whenGetRandomJokeByCriteriaThenReturnDtoList() {
        var jokes = jokeDataFactory.jokes();
        var categories = categoryRepository.findAll();
        categories.get(0).addJoke(jokes.get(0));
        categories.get(1).addJoke(jokes.get(1));
        categories.get(2).addJoke(jokes.get(2));
        jokeRepository.saveAll(jokes);
        var expectedCatNames = categories.stream().map(Category::getName).toArray(String[]::new);
        var actual = jokeService.getRandomJokeByCriteria(getCriteria()).getBody();
        assertThat(actual)
                .isNotNull()
                .hasSize(3);
        assertThat(actual)
                .flatExtracting(JokeDto::getCategory)
                .containsExactlyInAnyOrder(expectedCatNames);
    }

    @Test
    void whenGetJokeByIdThenReturnJokeDto() {
        var joke = jokeDataFactory.joke();
        categoryRepository.findById(1L)
                          .ifPresent(category -> {
                              category.addJoke(joke);
                              jokeRepository.save(joke);
                          });
        assertThat(jokeService.getJokeById(1L).getBody())
                .satisfies(actual -> {
                    assertThat(actual).isNotNull();
                    assertThat(actual.getJoke()).isEqualTo(joke.getValue());
                    assertThat(actual.getCategory()).isEqualTo(joke.getCategory().getName());

                });
    }

    @Test
    void whenGetJokeByIdThenThrowBusinessException() {
        jokeRepository.save(jokeDataFactory.joke());
        assertThatThrownBy(() -> jokeService.getJokeById(0L))
                .isInstanceOf(BusinessException.class)
                .hasMessageContaining("Шутка про Чака с идентификатором 0 не найдена!");
    }

    private JokeQueryCriteria getCriteria() {
        var jokeilter = new StringFilter();
        jokeilter.setContains("some-value");
        var categoryFilter = new StringFilter();
        categoryFilter.setIn(List.of("dev", "music", "sport"));
        var criteria = new JokeQueryCriteria();
        criteria.setJokeName(jokeilter);
        criteria.setCategoryName(categoryFilter);
        return criteria;
    }

}