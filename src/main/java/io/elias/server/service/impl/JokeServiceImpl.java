package io.elias.server.service.impl;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.Expressions;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.elias.server.client.JokeClient;
import io.elias.server.dto.JokeDto;
import io.elias.server.exception.BusinessException;
import io.elias.server.exception.ErrorType;
import io.elias.server.mapper.JokeMapper;
import io.elias.server.model.Joke;
import io.elias.server.repository.CategoryRepository;
import io.elias.server.repository.JokeRepository;
import io.elias.server.service.JokeService;
import io.elias.server.service.MessageSourceHelper;

@Service
@RequiredArgsConstructor
@Slf4j
public class JokeServiceImpl implements JokeService {

    private final JokeRepository jokeRepository;

    private final CategoryRepository categoryRepository;

    private final JokeMapper jokeMapper;

    private final JokeClient jokeClient;

    private final MessageSourceHelper messageSourceHelper;

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
    public ResponseEntity<JokeDto> getRandomJoke() {
        return ResponseEntity.ok(jokeMapper.map(jokeRepository.findRandomJoke()));
    }

    private void getAndSaveJoke(String categoryName) {
        var category =
                categoryRepository.findCategoryByName(categoryName)
                 .orElseThrow(() -> {
                    var errorType = ErrorType.CATEGORY_NOT_FOUND_BY_NAME;
                    var msg = messageSourceHelper.getMessage(errorType, categoryName);
                    log.warn(msg);
                    throw new BusinessException(errorType, msg);
                 });
        var joke =
                Joke.builder()
                    .value(jokeClient.getRandomJokeByCategory(category.getName()))
                    .category(category)
                    .build();
        jokeRepository.save(joke);
    }

}
