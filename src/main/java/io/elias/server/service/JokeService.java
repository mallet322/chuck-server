package io.elias.server.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import io.elias.server.dto.JokeDto;
import io.elias.server.dto.JokesGeneralStatistic;
import io.elias.server.filter.JokeQueryCriteria;

public interface JokeService {

    ResponseEntity<Void> createJoke(boolean flag, String category, JokeDto jokeDto);

    ResponseEntity<JokeDto> getJokeById(Long id);

    ResponseEntity<JokeDto> getRandomJoke();

    ResponseEntity<JokeDto> getRandomJokeByCategory(String categoryName);

    ResponseEntity<List<JokesGeneralStatistic>> getJokesCountStatistics();

    ResponseEntity<List<JokeDto>> getRandomJokeByCriteria(JokeQueryCriteria criteria);

}
