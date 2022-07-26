package ru.elias.server.service;

import java.util.List;

import org.springframework.http.ResponseEntity;
import ru.elias.server.dto.CategoryDto;

public interface CategoryService {

    ResponseEntity<CategoryDto> getCategoryByName(String name);

    ResponseEntity<List<CategoryDto>> getAllCategories();

    ResponseEntity<Void> createCategories(boolean flag, CategoryDto categoryRequest);

}
