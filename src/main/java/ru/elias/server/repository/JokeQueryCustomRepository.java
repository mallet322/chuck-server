package ru.elias.server.repository;

import java.util.List;

import com.querydsl.core.types.Predicate;
import io.micrometer.core.annotation.Timed;
import ru.elias.server.dto.JokesGeneralStatistic;
import ru.elias.server.model.Joke;

public interface JokeQueryCustomRepository {

    @Timed("gettingJokesByPredicateDataLayerGauge")
    List<Joke> findJokesByPredicate(Predicate predicate);

    @Timed("gettingCountByCategoriesDataLayerGauge")
    List<JokesGeneralStatistic> countByCategories();

}
