package io.elias.server.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import io.elias.server.model.Category;

public interface CategoryRepository extends JpaRepository<Category, UUID> {

    Category findCategoryByName(String name);

}
