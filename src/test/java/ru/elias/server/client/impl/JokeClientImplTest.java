package ru.elias.server.client.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import ru.elias.server.client.JokeClient;

@SpringBootTest
class JokeClientImplTest {

    @Autowired
    private JokeClient jokeClient;

    @Test
    void getRandomJoke() {
        var resp = jokeClient.getRandomJoke();
        Assertions.assertEquals("123", resp);
    }

}