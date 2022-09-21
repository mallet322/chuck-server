package ru.elias.server.service;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import ru.elias.server.dto.JokeDto;
import ru.elias.server.dto.JokesGeneralStatistic;
import ru.elias.server.dto.report.JokesByCategoriesReportData;
import ru.elias.server.filter.JokeQueryCriteria;

public interface JokeService {

    ResponseEntity<Void> createJoke(boolean flag, String category, JokeDto jokeDto);

    ResponseEntity<JokeDto> getJokeById(Long id);

    ResponseEntity<JokeDto> getRandomJoke();

    ResponseEntity<JokeDto> getRandomJokeByCategory(String categoryName);

    ResponseEntity<List<JokesGeneralStatistic>> getJokesCountStatistics();

    ResponseEntity<List<JokeDto>> getRandomJokeByCriteria(JokeQueryCriteria criteria);

    Optional<List<JokesByCategoriesReportData>> getAllJokesByCategory(String categoryName);

}
