package io.elias.server.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.elias.server.client.JokeClient;
import io.elias.server.dto.CategoryDto;
import io.elias.server.exception.BusinessException;
import io.elias.server.exception.ErrorType;
import io.elias.server.mapper.CategoryMapper;
import io.elias.server.model.Category;
import io.elias.server.repository.CategoryRepository;
import io.elias.server.service.CategoryService;
import io.elias.server.service.MessageSourceHelper;

@Slf4j
@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    private final JokeClient jokeClient;

    private final CategoryMapper categoryMapper;

    private final MessageSourceHelper messageSourceHelper;

    @Override
    @Transactional
    public ResponseEntity<CategoryDto> getCategoryByName(String name) {
        return ResponseEntity.ok(
                categoryRepository.findCategoryByName(name)
                                  .map(categoryMapper::map)
                                  .orElseThrow(() -> {
                                      var errorType = ErrorType.CATEGORY_NOT_FOUND_BY_NAME;
                                      var msg = messageSourceHelper.getMessage(errorType, name);
                                      log.warn(msg);
                                      throw new BusinessException(errorType, msg);
                                  })
        );
    }

    @Override
    @Transactional
    public ResponseEntity<List<CategoryDto>> getAllCategories() {
        return ResponseEntity.ok(
                categoryRepository.findAll()
                                  .stream()
                                  .map(categoryMapper::map)
                                  .collect(Collectors.toList())
        );
    }

    @Override
    @Transactional
    public ResponseEntity<Void> createCategories(boolean flag, CategoryDto categoryRequest) {
        if (flag) {
            saveCategoriesFromIntegration();
        } else {
            saveCategory(categoryRequest);
        }
        return ResponseEntity.ok().build();
    }

    private void saveCategory(CategoryDto categoryRequest) {
        var category = categoryMapper.map(categoryRequest);
        categoryRepository.save(category);
    }

    private void saveCategoriesFromIntegration() {
        var categories = jokeClient.getAllCategories()
                                   .stream()
                                   .filter(el -> !getExistingCategoryNames().contains(el))
                                   .map(s -> Category.builder()
                                                     .name(s)
                                                     .build())
                                   .collect(Collectors.toList());
        categoryRepository.saveAll(categories);
    }

    private List<String> getExistingCategoryNames() {
        return categoryRepository.findAll()
                                 .stream()
                                 .map(Category::getName)
                                 .collect(Collectors.toList());
    }

}
