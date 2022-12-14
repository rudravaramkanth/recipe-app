package com.rk.recipeapp.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Recipe {

    private Long recipeId;
    @NotNull
    @NotBlank
    @Length(min = 2, max = 50)
    private String recipeName;
    @NotNull
    @NotBlank
    @Length(min = 2, max = 50)
    private String instructions;
    @Min(value = 1)
    @Max(value = 20)
    private Integer servings;
    private Boolean isVeg;
    @Valid
    @NotEmpty
    @Size(min = 1)
    private List<String> ingredients;

}
