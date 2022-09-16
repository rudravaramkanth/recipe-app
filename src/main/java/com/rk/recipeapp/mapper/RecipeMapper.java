package com.rk.recipeapp.mapper;

import com.rk.recipeapp.domain.Recipe;
import com.rk.recipeapp.entiry.RecipeEntity;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class RecipeMapper {

    private RecipeMapper() {
        throw new IllegalStateException("Utility class");
    }

    public static Recipe mapToRecipe(RecipeEntity entity) {
        return Recipe.builder().recipeId(entity.getId())
                .recipeName(entity.getRecipeName())
                .instructions(entity.getInstructions())
                .servings(entity.getServings())
                .isVeg(entity.getIsVeg())
                .ingredients(entity.getIngredients() != null ? List.of(entity.getIngredients().split(","))
                        : new ArrayList<>())
                .build();
    }

    public static RecipeEntity mapToRecipeEntity(Recipe recipe) {
        return RecipeEntity.builder().id(recipe.getRecipeId())
                .recipeName(recipe.getRecipeName())
                .instructions(recipe.getInstructions())
                .servings(recipe.getServings())
                .isVeg(recipe.getIsVeg())
                .ingredients(recipe.getIngredients() != null ? String.join(",", recipe.getIngredients()) : "")
                .build();
    }

}
