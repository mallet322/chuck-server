package io.elias.server.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import io.elias.server.model.Joke;

public interface JokeRepository extends JpaRepository<Joke, UUID> {

    Joke findJokeByValue(String name);

    List<Joke> findJokesByCategory_Name(String name);

}
