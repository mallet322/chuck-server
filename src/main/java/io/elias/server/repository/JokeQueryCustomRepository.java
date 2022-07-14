package io.elias.server.repository;

import java.util.List;
import com.querydsl.core.types.Predicate;

import io.elias.server.model.Joke;

public interface JokeQueryCustomRepository {

    List<Joke> findJokesByPredicate(Predicate predicate);

}
