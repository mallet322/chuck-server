package io.elias.server.service;

import java.util.List;

import io.elias.server.model.Category;

public interface CategoryService {

    Category getCategoryByName(String name);

    List<Category> findAll();

    Category createCategory(Category category);

    void createAllCategory();

}
