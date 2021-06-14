package io.elias.server.repository;

import java.util.List;
import java.util.UUID;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import io.elias.server.model.Category;

@RunWith(SpringRunner.class)
@DataJpaTest
public class CategoryRepositoryTest {

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    public void shouldGetAllCategoriesFromInMemoryDB_success() {

        var categories = List.of(
                new Category(UUID.randomUUID(), "some-cat0", null),
                new Category(UUID.randomUUID(), "some-cat1", null),
                new Category(UUID.randomUUID(), "some-cat2", null));

        categories.forEach(category -> categoryRepository.save(category));

        var actualCategoriesList = categoryRepository.findAll();

        Assert.assertNotNull(actualCategoriesList);
        Assert.assertEquals("some-cat0", actualCategoriesList.get(0).getName());
        Assert.assertEquals("some-cat1", actualCategoriesList.get(1).getName());
        Assert.assertEquals("some-cat2", actualCategoriesList.get(2).getName());

    }

    @Test
    public void shouldGetCategoryByIdFromInMemoryDB_success() {
        var expCat = new Category(UUID.randomUUID(), "some-cat", null);
        categoryRepository.save(expCat);

        var actCat = categoryRepository.findById(expCat.getId());

        Assert.assertNotNull(actCat);
        actCat.ifPresent(category -> Assert.assertEquals(expCat.getId(), category.getId()));
    }

    @Test
    public void shouldGetCategoryByNameFromInMemoryDB_success() {
        var expCat = new Category(UUID.randomUUID(), "some-cat", null);
        categoryRepository.save(expCat);

        var actCat = categoryRepository.findCategoryByName(expCat.getName());

        Assert.assertNotNull(actCat);
        Assert.assertEquals(expCat.getName(), actCat.getName());
    }

}