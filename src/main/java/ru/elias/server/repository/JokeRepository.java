package ru.elias.server.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;

import ru.elias.server.model.Joke;

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

    @Query("select count(j) "
            + "from Category c "
            + "left join c.jokes j "
            + "where c.name = :categoryName "
            + "group by c.id")
    Long countJokeByCategoryName(@Param("categoryName") String categoryName);

}
