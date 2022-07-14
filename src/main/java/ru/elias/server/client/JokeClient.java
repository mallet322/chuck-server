package ru.elias.server.client;

import java.util.List;

/**
 * Клиент для общения с сервисом шуток про чака
 */
public interface JokeClient {

    String getRandomJoke();

    String getRandomJokeByCategory(String category);

    List<String> getAllCategories();
}
