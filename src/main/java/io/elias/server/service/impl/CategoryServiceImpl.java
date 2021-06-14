package io.elias.server.service.impl;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import io.elias.server.model.Category;
import io.elias.server.repository.CategoryRepository;
import io.elias.server.service.CategoryService;
import io.elias.server.service.RequestHelper;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    private final RequestHelper helper;

    @Override
    public Category getCategoryByName(String name) {
        return categoryRepository.findCategoryByName(name);
    }

    @Override
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    public Category createCategory(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    public void createAllCategory() {
        List<Category> categories = helper.getAllCategories()
                                          .stream()
                                          .map(s -> new Category(UUID.randomUUID(), s, null))
                                          .collect(Collectors.toList());
        categories.forEach(category -> categoryRepository.save(category));
    }

}
