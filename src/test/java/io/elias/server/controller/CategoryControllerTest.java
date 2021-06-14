package io.elias.server.controller;

import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import io.elias.server.model.Category;
import io.elias.server.model.Joke;
import io.elias.server.service.JokeService;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class CategoryControllerTest {

    @Autowired
    @MockBean
    private JokeService jokeService;

    @Autowired
    private CategoryController categoryController;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void getAllCategories() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/category"))
               .andDo(MockMvcResultHandlers.print())
               .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void showJokeFromCategory() throws Exception {
        Mockito.when(jokeService.getJokeByCategoryFromChuckAPI(Mockito.anyString()))
               .thenReturn(buildJokeEntity("DEV joke"));

        mockMvc.perform(MockMvcRequestBuilders.get("/category/dev"))
               .andDo(MockMvcResultHandlers.print())
               .andExpect(MockMvcResultMatchers.status().isOk())
               .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("DEV joke")));
    }

    private Joke buildJokeEntity(String name) {
        var category = Category.builder()
                               .name("dev")
                               .build();
        return Joke.builder()
                   .value(name)
                   .category(category)
                   .build();
    }

}