package ru.elias.server.client;

import java.util.List;

import reactor.core.publisher.Mono;

/**
 * Клиент для общения с сервисом шуток про чака
 */
public interface JokeReactiveClient {

    Mono<String> getRandomJoke();

    Mono<String> getRandomJokeByCategory(String category);

    Mono<List<String>> getAllCategories();

}
