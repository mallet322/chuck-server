package ru.elias.server.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import com.querydsl.core.BooleanBuilder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ru.elias.server.client.JokeClient;
import ru.elias.server.dto.JokeDto;
import ru.elias.server.dto.JokesGeneralStatistic;
import ru.elias.server.exception.BusinessException;
import ru.elias.server.exception.ErrorType;
import ru.elias.server.filter.JokeQueryCriteria;
import ru.elias.server.filter.common.CommonBooleanBuilder;
import ru.elias.server.mapper.JokeMapper;
import ru.elias.server.model.Category;
import ru.elias.server.model.Joke;
import ru.elias.server.repository.CategoryRepository;
import ru.elias.server.repository.JokeQueryCustomRepository;
import ru.elias.server.repository.JokeRepository;
import ru.elias.server.service.JokeService;
import ru.elias.server.service.MessageSourceHelper;
import ru.elias.server.util.QEntities;

@Service
@RequiredArgsConstructor
@Slf4j
public class JokeServiceImpl implements JokeService {

    private final JokeRepository jokeRepository;

    private final JokeQueryCustomRepository jokeQueryCustomRepository;

    private final CategoryRepository categoryRepository;

    private final JokeMapper jokeMapper;

    private final JokeClient jokeClient;

    private final MessageSourceHelper messageSourceHelper;

    private final CommonBooleanBuilder commonBooleanBuilder;

    @Override
    @Transactional
    public ResponseEntity<Void> createJoke(boolean flag, String category, JokeDto jokeDto) {
        if (flag) {
            getAndSaveJoke(category);
        } else {
            jokeRepository.save(jokeMapper.map(jokeDto));
        }
        return ResponseEntity.ok().build();
    }

    @Override
    @Transactional
    public ResponseEntity<JokeDto> getRandomJoke() {
        return ResponseEntity.ok(jokeMapper.map(jokeRepository.findRandomJoke()));
    }

    @Override
    @Transactional
    public ResponseEntity<JokeDto> getRandomJokeByCategory(String categoryName) {
        var category = getCategory(categoryName);
        return ResponseEntity.ok(
                jokeMapper.map(jokeRepository.findRandomJokeByCategoryId(category.getId()))
        );
    }

    @Override
    @Transactional
    public ResponseEntity<List<JokesGeneralStatistic>> getJokesCountStatistics() {
        return ResponseEntity.ok(categoryRepository.findAll()
                                                   .stream()
                                                   .map(this::getSummaryJokesOnCategories)
                                                   .collect(Collectors.toList())
        );
    }

    @Override
    public ResponseEntity<List<JokeDto>> getRandomJokeByCriteria(JokeQueryCriteria criteria) {
        BooleanBuilder filter = getBooleanBuilder(criteria);
        return ResponseEntity.ok(jokeQueryCustomRepository.findJokesByPredicate(filter)
                                                          .stream()
                                                          .map(jokeMapper::map)
                                                          .collect(Collectors.toList()));
    }

    @Override
    @Transactional
    public ResponseEntity<JokeDto> getJokeById(Long id) {
        var predicate = QEntities.JOKE.id.eq(id);
        return ResponseEntity.ok(
                jokeRepository.findOne(predicate)
                              .map(jokeMapper::map)
                              .orElseThrow(() -> {
                                  var errorType = ErrorType.JOKE_NOT_FOUND_BY_ID;
                                  var msg = messageSourceHelper.getMessage(errorType, id);
                                  log.error(msg);
                                  throw new BusinessException(errorType, msg);
                              })
        );
    }

    private void getAndSaveJoke(String categoryName) {
        var category = getCategory(categoryName);
        var joke =
                Joke.builder()
                    .value(jokeClient.getRandomJokeByCategory(category.getName()))
                    .category(category)
                    .build();
        jokeRepository.save(joke);
    }

    private Category getCategory(String categoryName) {
        return categoryRepository.findCategoryByName(categoryName)
                                 .orElseThrow(() -> {
                                     var errorType = ErrorType.CATEGORY_NOT_FOUND_BY_NAME;
                                     var msg = messageSourceHelper.getMessage(
                                             errorType,
                                             categoryName
                                     );
                                     log.error(msg);
                                     throw new BusinessException(errorType, msg);
                                 });
    }

    private JokesGeneralStatistic getSummaryJokesOnCategories(Category category) {
        return new JokesGeneralStatistic(
                category.getName(),
                jokeRepository.countJokeByCategoryName(category.getName())
        );
    }

    private BooleanBuilder getBooleanBuilder(JokeQueryCriteria criteria) {
        var booleanBuilder = new BooleanBuilder();
        commonBooleanBuilder.andMatchStringFilter(booleanBuilder,
                                                  criteria.getJokeName(),
                                                  QEntities.JOKE.value);
        commonBooleanBuilder.andMatchStringFilter(booleanBuilder,
                                                  criteria.getCategoryName(),
                                                  QEntities.CATEGORY.name);
        return booleanBuilder;
    }

}
