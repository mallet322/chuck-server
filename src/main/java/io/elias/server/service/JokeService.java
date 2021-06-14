package io.elias.server.service;

import java.util.List;

import io.elias.server.model.Joke;

public interface JokeService {

    Joke getJokeByCategoryFromChuckAPI(String name);

    Joke getRandomJokeFromChuckAPI();

    void createJoke(Joke joke);

    List<Joke> getAllJokes();

    Joke getRandomJoke();

    List<Joke> getJokesByCategory(String name);

}
