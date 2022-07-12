package io.elias.server.client.impl;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import io.elias.server.client.JokeClient;

@Component
@RequiredArgsConstructor
@Slf4j
public class JokeClientImpl implements JokeClient {

    public static final String PATH = "https://api.chucknorris.io/jokes";

    public static final String RANDOM_JOKE = "/random";

    public static final String CATEGORIES = "/categories";

    public static final String RANDOM_JOKE_BY_CATEGORY = "/random?category={category}";

    private final RestTemplate restTemplate;

    @Override
    public String getRandomJoke() {
        return restTemplate.getForEntity(PATH + RANDOM_JOKE, String.class)
                           .getBody();
    }

    @Override
    public String getRandomJokeByCategory(String category) {
        return restTemplate.getForEntity(PATH + RANDOM_JOKE_BY_CATEGORY, String.class, category)
                           .getBody();
    }

    @Override
    public List<String> getAllCategories() {
        var categories =
                Objects.requireNonNull(
                        restTemplate.getForEntity(PATH + CATEGORIES, String[].class)
                                    .getBody()
                );
        return Arrays.asList(categories);
    }

}
