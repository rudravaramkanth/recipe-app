package com.rk.recipeapp.filter;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecipeFilter {

    private Integer servings;
    private Boolean isVeg;
    private String instructions;
    private String includeIngredient;
    private String excludeIngredient;
}
