package ru.elias.server.client.impl;

import java.util.List;

import io.micrometer.core.annotation.Timed;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import ru.elias.server.client.JokeReactiveClient;
import static ru.elias.server.util.ApiPathConstants.CATEGORIES;
import static ru.elias.server.util.ApiPathConstants.JOKES;
import static ru.elias.server.util.ApiPathConstants.RANDOM_JOKE;

@Component
@RequiredArgsConstructor
@Slf4j
public class JokeReactiveClientImpl implements JokeReactiveClient {

    private final WebClient jokeWebClient;

    @Timed("getRandomJokeIntegrationGauge")
    @Override
    public Mono<String> getRandomJoke() {
        return jokeWebClient.get()
                            .uri(uriBuilder -> uriBuilder.path(JOKES + RANDOM_JOKE)
                                                         .build())
                            .retrieve()
                            .bodyToMono(new ParameterizedTypeReference<String>() {})
                            .doOnError(error -> log.error(error.getMessage(), error));
    }

    @Timed("getRandomJokeByCategoryIntegrationGauge")
    @Override
    public Mono<String> getRandomJokeByCategory(String category) {
        return jokeWebClient.get()
                            .uri(uriBuilder -> uriBuilder
                                    .path(JOKES + RANDOM_JOKE)
                                    .queryParam("category", category)
                                    .build())
                            .retrieve()
                            .bodyToMono(new ParameterizedTypeReference<String>() {})
                            .doOnError(error -> log.error(error.getMessage(), error));
    }

    @Timed("getAllCategoriesIntegrationGauge")
    @Override
    public Mono<List<String>> getAllCategories() {
        return jokeWebClient.get()
                            .uri(uriBuilder -> uriBuilder
                                    .path(JOKES + CATEGORIES)
                                    .build())
                            .retrieve()
                            .bodyToMono(new ParameterizedTypeReference<List<String>>() {})
                            .doOnError(error -> log.error(error.getMessage(), error));
    }

}
