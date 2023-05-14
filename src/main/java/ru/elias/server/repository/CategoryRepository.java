package ru.elias.server.repository;

import java.util.Optional;

import io.micrometer.core.annotation.Timed;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.elias.server.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    @Timed
    Optional<Category> findCategoryByName(String name);

}
