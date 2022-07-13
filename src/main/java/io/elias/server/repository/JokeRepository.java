package io.elias.server.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import io.elias.server.model.Joke;

public interface JokeRepository extends JpaRepository<Joke, UUID>, QuerydslPredicateExecutor<Joke> {

    @Query(nativeQuery = true,
           value = "select j.* "
                   + "from jokes j "
                   + "order by random() "
                   + "limit 1")
    Joke findRandomJoke();

    @Query(nativeQuery = true,
           value = "select j.* "
                   + "from jokes j "
                   + "where category_id = ?1 "
                   + "order by random() "
                   + "limit 1")
    Joke findRandomJokeByCategoryId(Long categoryId);

}
