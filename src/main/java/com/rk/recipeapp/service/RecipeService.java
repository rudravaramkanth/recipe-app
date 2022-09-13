package com.rk.recipeapp.service;

import com.rk.recipeapp.domain.Recipe;
import com.rk.recipeapp.entiry.RecipeEntity;
import com.rk.recipeapp.exceptions.RecipeNotFoundException;
import com.rk.recipeapp.filter.RecipeFilter;
import com.rk.recipeapp.filter.RecipeSpecification;
import com.rk.recipeapp.mapper.RecipeMapper;
import com.rk.recipeapp.repository.RecipeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
@Slf4j
@Service
public class RecipeService {
    @Autowired
    RecipeRepository recipeRepository;


    public List<Recipe> viewRecipes() {
         log.info("RecipeService::viewRecipes()");
        var entities = recipeRepository.findAll();
        return entities.stream().map(RecipeMapper::mapToRecipe).collect(Collectors.toList());
    }
    public Recipe createRecipe(Recipe recipe) {
        log.info("RecipeService::createRecipe()");
        var recipeEntity = RecipeMapper.mapToRecipeEntity(recipe);
        var entity = recipeRepository.save(recipeEntity);
        return RecipeMapper.mapToRecipe(entity);
    }

    public void updateRecipe(Recipe recipe, Long id) {
        log.info("RecipeService::updateRecipe()");
        var optionalRecipeEntity = recipeRepository.findById(id);
        if (optionalRecipeEntity.isEmpty()) {
            throw new RecipeNotFoundException("recipe is not found for recipe Id : " + id);
        }
        var foundEntity = optionalRecipeEntity.get();

        foundEntity.setId(id);
        foundEntity.setRecipeName(recipe.getRecipeName());
        foundEntity.setInstructions(recipe.getInstructions());
        foundEntity.setServings(recipe.getServings());
        foundEntity.setIsVeg(recipe.getIsVeg());
        foundEntity.setIngredients(
                recipe.getIngredients() != null ? String.join(",", recipe.getIngredients()) : "");
         RecipeMapper.mapToRecipe(recipeRepository.save(foundEntity));
    }

    public void deleteRecipe(Long id) {
       log.info("RecipeService::deleteRecipe()");
        Optional<RecipeEntity> optionalRecipeEntity = recipeRepository.findById(id);
        if (optionalRecipeEntity.isEmpty()) {
           throw new RecipeNotFoundException("recipe is not found for recipe Id : " + id);
        }
        recipeRepository.deleteById(id);
    }

    public List<Recipe> searchRecipes(final RecipeFilter recipeFilter) {
        log.info("RecipeService::searchRecipes()");
        var entities = recipeRepository.findAll(RecipeSpecification.getRecipeByCriteria(recipeFilter));
        return entities.stream().map(RecipeMapper::mapToRecipe).collect(Collectors.toList());
    }

}
