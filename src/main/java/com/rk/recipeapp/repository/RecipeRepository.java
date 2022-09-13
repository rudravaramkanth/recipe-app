package com.rk.recipeapp.repository;

import com.rk.recipeapp.entiry.RecipeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface RecipeRepository extends JpaRepository<RecipeEntity, Long>, JpaSpecificationExecutor<RecipeEntity> {
}
