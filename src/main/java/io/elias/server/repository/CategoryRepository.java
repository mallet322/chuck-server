package io.elias.server.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import io.elias.server.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    Optional<Category> findCategoryByName(String name);

}
