package ru.elias.server.controller.api;

import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.elias.server.dto.JokeDto;
import ru.elias.server.dto.JokesGeneralStatistic;
import ru.elias.server.filter.JokeQueryCriteria;
import ru.elias.server.service.JokeService;
import ru.elias.server.util.ApiPathConstants;

@Tag(name = "Шутки")
@RestController
@RequiredArgsConstructor
@RequestMapping(ApiPathConstants.API_V_1 + ApiPathConstants.JOKES)
public class JokeRestController {

    private final JokeService jokeService;

    @Operation(summary = "Получение шутки по идентификатору")
    @GetMapping(ApiPathConstants.BY_ID)
    public ResponseEntity<JokeDto> getJokeById(
            @PathVariable("id") @Parameter(description = "ID шутки") Long id) {
        return jokeService.getJokeById(id);
    }

    @Operation(summary = "Получение случайной шутки")
    @GetMapping(ApiPathConstants.RANDOM)
    public ResponseEntity<JokeDto> getRandomJoke() {
        return jokeService.getRandomJoke();
    }

    @Operation(summary = "Получение случайной шутки по наименовании категории")
    @GetMapping(ApiPathConstants.RANDOM + ApiPathConstants.BY_CATEGORY_NAME)
    public ResponseEntity<JokeDto> getRandomJokeByCategory(@PathVariable("categoryName")
                                                           @Parameter(description = "Наименование категории")
                                                                       String categoryName) {
        return jokeService.getRandomJokeByCategory(categoryName);
    }

    @Operation(summary = "Создание шутки")
    @PostMapping
    public ResponseEntity<Void> create(
            @Parameter(description = "Режим создания шутки (авто/ручное)")
            @RequestParam(value = "auto") boolean flag,
            @Parameter(description = "Наименование категории (режим авто)")
            @RequestParam(value = "category", required = false) String category,
            @RequestBody(required = false) JokeDto request) {
        return jokeService.createJoke(flag, category, request);
    }

    @Operation(summary = "Получение общего количества шуток для каждой категории")
    @GetMapping(ApiPathConstants.STATISTICS)
    public ResponseEntity<List<JokesGeneralStatistic>> getJokesCountStatistics() {
        return jokeService.getJokesCountStatistics();
    }

    @Operation(summary = "Получение списка шуток по критерию")
    @GetMapping(ApiPathConstants.QUERY)
    public ResponseEntity<List<JokeDto>> getJokesByCriteria(@ParameterObject JokeQueryCriteria criteria) {
        return jokeService.getRandomJokeByCriteria(criteria);
    }

}
