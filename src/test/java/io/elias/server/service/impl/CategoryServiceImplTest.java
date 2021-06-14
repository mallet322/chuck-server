package io.elias.server.service.impl;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import io.elias.server.model.Category;
import io.elias.server.repository.CategoryRepository;
import io.elias.server.service.CategoryService;
import io.elias.server.service.RequestHelper;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CategoryServiceImplTest {

    @Autowired
    @MockBean
    private CategoryRepository categoryRepository;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    @MockBean
    private RequestHelper helper;

    @Test
    public void shouldGetCategoryByName_success() {
        var expectedCategory = buildCategoryEntity("dev");

        Mockito.when(categoryRepository.findCategoryByName(Mockito.anyString())).thenReturn(expectedCategory);

        var actualCategory = categoryService.getCategoryByName("dev");

        Assert.assertNotNull(actualCategory);
        Assert.assertEquals(expectedCategory.getName(), actualCategory.getName());
    }

    @Test
    public void shouldGetAllCategories_success() {
        var cat0 = buildCategoryEntity("dev");
        var cat1 = buildCategoryEntity("sport");

        Mockito.when(categoryRepository.findAll()).thenReturn(List.of(cat0, cat1));
        var categories = categoryService.findAll();

        Assert.assertNotNull(categories);
        Assert.assertEquals(2, categories.size());
        Assert.assertEquals(cat0, categories.get(0));
    }

    @Test
    public void shouldCreateNewCategory_success() {
        var expectedCategory = buildCategoryEntity("some-category");
        Mockito.when(categoryRepository.save(Mockito.any(Category.class))).thenReturn(expectedCategory);
        var returned = categoryService.createCategory(expectedCategory);
        Assert.assertNotNull(returned.getName());
        Assert.assertEquals(expectedCategory.getName(), returned.getName());
    }

    @Test
    public void shouldCreateAllCategories_success() {
        Mockito.when(helper.getAllCategories())
               .thenReturn(List.of("some-category0", "some-category1", "some-category2"));
        categoryService.createAllCategory();
        Mockito.verify(categoryRepository, Mockito.times(3)).save(Mockito.any(Category.class));
    }

    private Category buildCategoryEntity(String name) {
        return Category.builder()
                       .name(name)
                       .build();
    }

}