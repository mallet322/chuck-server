package io.elias.server.service;

import org.springframework.http.ResponseEntity;

import io.elias.server.dto.JokeDto;

public interface JokeService {

    ResponseEntity<Void> createJoke(boolean flag, String category, JokeDto jokeDto);

    ResponseEntity<JokeDto> getRandomJoke();

}
