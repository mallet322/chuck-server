package io.elias.server.service.impl;

import java.util.List;
import java.util.Random;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import io.elias.server.client.JokeClient;
import io.elias.server.model.Joke;
import io.elias.server.repository.CategoryRepository;
import io.elias.server.repository.JokeRepository;
import io.elias.server.service.JokeService;

@Service
@RequiredArgsConstructor
@Slf4j
public class JokeServiceImpl implements JokeService {

    private final JokeRepository jokeRepository;

    private final CategoryRepository categoryRepository;

    private final JokeClient jokeClient;

    @Override
    public Joke getJokeByCategoryFromChuckAPI(String name) {
        var category = categoryRepository.findCategoryByName(name);
        String value = jokeClient.getRandomJokeByCategory(category.get().getName());

        var joke = Joke.builder()
                       .value(value)
                       .category(category.get())
                       .build();

        var existingJoke = jokeRepository.findJokeByValue(joke.getValue());

        if (existingJoke != null) {
            log.info("This joke is already in the database");
            if (existingJoke.getValue().isEmpty()) {
                log.info("This joke with an empty value");
            }
        } else {
            jokeRepository.save(joke);
        }

        return joke;
    }

    @Override
    public Joke getRandomJokeFromChuckAPI() {
        return Joke.builder()
                   .value(jokeClient.getRandomJoke())
                   .build();
    }

    @Override
    public void createJoke(Joke joke) {
        jokeRepository.save(joke);
    }

    @Override
    public List<Joke> getAllJokes() {
        return jokeRepository.findAll();
    }

    @Override
    public Joke getRandomJoke() {
        var jokes = jokeRepository.findAll();
        return jokes.get(new Random().nextInt(jokes.size()));
    }

    @Override
    public List<Joke> getJokesByCategory(String name) {
        return jokeRepository.findJokesByCategoryName(name);
    }

}
