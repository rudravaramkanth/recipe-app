package com.rk.recipeapp.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.rk.recipeapp.domain.Recipe;
import com.rk.recipeapp.exceptions.CustomizeExceptionHandler;
import com.rk.recipeapp.service.RecipeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
class RecipeControllerITTest {

  @InjectMocks
  RecipeController recipesController;
  @Mock
  RecipeService recipeService;

  @Autowired
  MockMvc mockMvc;

  @BeforeEach
  public void setUp() {
    mockMvc = MockMvcBuilders.standaloneSetup(recipesController)
        .setControllerAdvice(new CustomizeExceptionHandler()).build();
  }

  @Test
  void getRecipeTest() throws Exception {
    ObjectMapper mapper = new ObjectMapper();
    mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
    ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
    String requestJson = ow.writeValueAsString(getRecipe());
    MockHttpServletResponse response = mockMvc
        .perform(get("/api/v1/recipes").contentType(APPLICATION_JSON).content(requestJson))
        .andReturn().getResponse();
    assertEquals(HttpStatus.OK.value(), response.getStatus());
  }

  @Test
  void createRecipeTest() throws Exception {
    ObjectMapper mapper = new ObjectMapper();
    mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
    ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
    String requestJson = ow.writeValueAsString(getRecipe());
    MockHttpServletResponse response = mockMvc
        .perform(post("/api/v1/recipes").contentType(APPLICATION_JSON).content(requestJson))
        .andReturn().getResponse();
    assertEquals(HttpStatus.CREATED.value(), response.getStatus());
  }

  @Test
  void updateRecipeTest() throws Exception {
    ObjectMapper mapper = new ObjectMapper();
    mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
    ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
    String requestJson = ow.writeValueAsString(getRecipe());
    MockHttpServletResponse response = mockMvc
        .perform(put("/api/v1/recipes/1").contentType(APPLICATION_JSON).content(requestJson))
        .andReturn().getResponse();
    assertEquals(HttpStatus.NO_CONTENT.value(), response.getStatus());
  }

  @Test
  void deleteRecipeTest() throws Exception {
    ObjectMapper mapper = new ObjectMapper();
    mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
    ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
    String requestJson = ow.writeValueAsString(getRecipe());
    MockHttpServletResponse response = mockMvc
        .perform(delete("/api/v1/recipes/1").contentType(APPLICATION_JSON).content(requestJson))
        .andReturn().getResponse();
    assertEquals(HttpStatus.OK.value(), response.getStatus());
  }

  private Recipe getRecipe() {
    Recipe recipe = new Recipe();
    recipe.setRecipeId(1L);
    recipe.setRecipeName("Salmon Soup");
    recipe.setInstructions("take bowl,take pan,salmon peices,chillpowder");
    recipe.setIngredients(List.of("Salmon"));
    recipe.setIsVeg(false);
    recipe.setServings(2);
      return recipe;
  }
}
