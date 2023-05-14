package ru.elias.server.repository;

import java.util.List;

import com.querydsl.core.types.Predicate;
import io.micrometer.core.annotation.Timed;
import ru.elias.server.dto.JokesGeneralStatistic;
import ru.elias.server.model.Joke;

public interface JokeQueryCustomRepository {

    @Timed("gettingJokesByPredicateMetric")
    List<Joke> findJokesByPredicate(Predicate predicate);

    @Timed(("gettingCountByCategoriesMetric"))
    List<JokesGeneralStatistic> countByCategories();

}
