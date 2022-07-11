package io.elias.server.client.impl;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import io.elias.server.client.JokeClient;
import io.elias.server.util.Constant;

@Component
@RequiredArgsConstructor
@Slf4j
public class JokeClientImpl implements JokeClient {

    private final RestTemplate restTemplate;

    @Override
    public String getRandomJoke() {
        return restTemplate.getForEntity(Constant.RANDOM_JOKE, String.class)
                           .getBody();
    }

    @Override
    public String getRandomJokeByCategory(String category) {
        return restTemplate.getForEntity(Constant.JOKE_FROM_CATEGORY, String.class, category)
                           .getBody();
    }

    @Override
    public List<String> getAllCategories() {
        var categories =
                Objects.requireNonNull(
                        restTemplate.getForEntity(Constant.CATEGORIES, String[].class).getBody()
                );
        return Arrays.asList(categories);
    }

}
