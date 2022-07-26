package ru.elias.server.service.impl.integration;

import java.util.List;

import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.ExpectedDataSet;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import ru.elias.server.AbstractDbRiderTest;
import ru.elias.server.dto.JokeDto;
import ru.elias.server.dto.JokesGeneralStatistic;
import ru.elias.server.exception.BusinessException;
import ru.elias.server.filter.JokeQueryCriteria;
import ru.elias.server.filter.base.StringFilter;
import ru.elias.server.model.Category;
import ru.elias.server.repository.CategoryRepository;
import ru.elias.server.repository.JokeRepository;
import ru.elias.server.service.impl.JokeServiceImpl;

class JokeServiceIntegrationTest extends AbstractDbRiderTest {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private JokeServiceImpl jokeService;

    @Test
    @DataSet(value = "data/yml/createJokeWithAutoModeIntegrationTest.yml")
    @ExpectedDataSet(value = "data/yml/createJokeWithAutoModeIntegrationTestExpected.yml",
                     ignoreCols = {"name", "created_at"})
    void whenCreateJokeWithAutoMode() {
        var result = jokeService.createJoke(true, "sport", null);
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    }

    @Test
    @DataSet(value = "data/yml/ethalonData.yml")
    void whenCreateJokeWithAutoModeThenNotCreateJokeAndThrowBusinessExeption() {
        categoryRepository.saveAll(dataFactory.categories());
        assertThatThrownBy(() -> jokeService.createJoke(true, "some-category", null))
                .isInstanceOf(BusinessException.class)
                .hasMessageContaining("Категория с названием some-category не найдена!");
    }

    @Test
    @DataSet(value = "data/yml/createJokeWithManualModeIntegrationTest.yml")
    @ExpectedDataSet(value = "data/yml/createJokeWithManualModeIntegrationTestExpected.yml",
                     ignoreCols = "created_at")
    void whenCreateJokeWithManualMode() {
        var dto = dataFactory.jokeDto();
        dto.setCategory("dev");
        var result = jokeService.createJoke(false, null, dto);
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    }

    @Test
    @DataSet(value = "data/yml/ethalonData.yml")
    void whenCreateJokeWithManualModeThenNotCreateJokeAndThrowBusinessExeption() {
        var dto = dataFactory.jokeDto();
        assertThatThrownBy(() -> jokeService.createJoke(false, null, dto))
                .isInstanceOf(BusinessException.class)
                .hasMessageContaining("Категория с названием some-category не найдена!");
    }

    @Test
    @DataSet(value = "data/yml/ethalonData.yml")
    void whenGetRandomJokeThenReturnJokeDto() {
        assertThat(jokeService.getRandomJoke().getBody())
                .satisfies(actual -> {
                    assertThat(actual).isNotNull();
                    assertThat(actual.getJoke()).isNotBlank();
                });
    }

    @Test
    @DataSet(value = "data/yml/ethalonData.yml")
    void whenGetRandomJokeByCategoryThenReturnJokeDto() {
        var category = categoryRepository.findById(1L).orElse(null);
        assertThat(category).isNotNull();
        assertThat(jokeService.getRandomJokeByCategory(category.getName()).getBody())
                .satisfies(actual -> {
                    assertThat(actual).isNotNull();
                    assertThat(actual.getJoke()).isNotBlank();
                    assertThat(actual.getCategory()).isEqualTo(category.getName());
                });
    }

    @Test
    @DataSet(value = "data/yml/ethalonData.yml")
    void whenGetJokesCountStatisticsThenReturnDtoWithStatistic() {
        var expectedCategoryNames = categoryRepository.findAll()
                                                      .stream()
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
    @DataSet(value = "data/yml/ethalonData.yml")
    void whenGetRandomJokeByCriteriaThenReturnDtoList() {
        var expectedCatNames =
                categoryRepository.findAll()
                                  .stream()
                                  .map(Category::getName)
                                  .filter(c -> c.equals("sport"))
                                  .toArray(String[]::new);
        var actual = jokeService.getRandomJokeByCriteria(getCriteria()).getBody();
        assertThat(actual)
                .isNotNull();
        assertThat(actual)
                .flatExtracting(JokeDto::getCategory)
                .containsExactlyInAnyOrder(expectedCatNames);
    }

    @Test
    @DataSet(value = "data/yml/ethalonData.yml")
    void whenGetJokeByIdThenReturnJokeDto() {
        assertThat(jokeService.getJokeById(3L).getBody())
                .satisfies(actual -> {
                    assertThat(actual).isNotNull();
                    assertThat(actual.getJoke()).isEqualTo("some-joke-3");
                    assertThat(actual.getCategory()).isEqualTo("animal");
                });
    }

    @Test
    @DataSet(value = "data/yml/ethalonData.yml")
    void whenGetJokeByIdThenThrowBusinessException() {
        assertThatThrownBy(() -> jokeService.getJokeById(0L))
                .isInstanceOf(BusinessException.class)
                .hasMessageContaining("Шутка про Чака с идентификатором 0 не найдена!");
    }

    private JokeQueryCriteria getCriteria() {
        var jokeFilter = new StringFilter();
        jokeFilter.setEndWith("joke-2");
        var categoryFilter = new StringFilter();
        categoryFilter.setIn(List.of("sport"));
        var criteria = new JokeQueryCriteria();
        criteria.setJokeName(jokeFilter);
        criteria.setCategoryName(categoryFilter);
        return criteria;
    }

}