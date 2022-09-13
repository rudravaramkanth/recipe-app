package com.rk.recipeapp.controller;

import com.rk.recipeapp.domain.Recipe;
import com.rk.recipeapp.filter.RecipeFilter;
import com.rk.recipeapp.service.RecipeService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/recipes")
public class RecipeController {
    @Autowired
    private RecipeService recipeService;

    /*
     * Get All Recipes
     */
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "View a list Recipes", response = Recipe.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "list of recipes"),
            @ApiResponse(code = 401, message = "You aren't authorized to view the recipes"),
            @ApiResponse(code = 403, message = "Access Forbidden for recipes")
    })
    public ResponseEntity<List<Recipe>> getRecipes() {

        return ResponseEntity.status(HttpStatus.OK).body(recipeService.viewRecipes());

    }

    /*
     * Create Recipe
     */
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Create Recipe")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Recipe Created."),
            @ApiResponse(code = 400, message = "Bad request.Invalid request params"),
            @ApiResponse(code = 500, message = "An internal error occurred.")
    })

    public ResponseEntity<Recipe> createRecipe(@Valid @RequestBody Recipe recipe) {
        log.debug("Request to add new recipe=" + recipe);
        return ResponseEntity.status(HttpStatus.CREATED).body(recipeService.createRecipe(recipe));
    }

    /*
     * update Recipe
     */
    @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Update Recipe")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "The recipe is updated"),
            @ApiResponse(code = 404, message = "The Recipe is not found"),
            @ApiResponse(code = 400, message = "Bad request. Invalid request params"),
            @ApiResponse(code = 500, message = "An internal error occurred.")
    })


    public ResponseEntity<Recipe> updateRecipe(@Valid @RequestBody Recipe recipe,
                                               @ApiParam(name = "id", value = "Recipe id", example = "1")
                                               @PathVariable Long id) {
        log.debug("update recipe Id : {}", id);
        recipeService.updateRecipe(recipe, id);
        return ResponseEntity.noContent().build();
    }

    /*
     * Delete Recipe
     */
    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Delete Recipe")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Recipe deleted."),
            @ApiResponse(code = 404, message = "The Recipe is not found"),
            @ApiResponse(code = 400, message = "Bad request, Invalid request params"),
            @ApiResponse(code = 500, message = "An internal error occurred.")
    })
    public ResponseEntity<Recipe> deleteRecipe(
            @ApiParam(name = "id", value = "Recipe id", example = "1")
            @PathVariable @Valid Long id) {
        log.debug("delete recipe Id : {}", id);
        recipeService.deleteRecipe(id);
        return ResponseEntity.ok().build();
    }


    @PostMapping(path = "/search", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Search in Recipes", response = Recipe.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "list of filtered recipes"),
            @ApiResponse(code = 401, message = "You aren't authorized to view the recipes"),
            @ApiResponse(code = 403, message = "Access Forbidden for recipes")
    })
    public List<Recipe> searchRecipes(@RequestBody RecipeFilter recipeFilter) {
        return recipeService.searchRecipes(recipeFilter);
    }


}
