package com.rk.recipeapp.filter;

import com.rk.recipeapp.entiry.RecipeEntity;
import lombok.Data;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

@Data

public class RecipeSpecification {

    private RecipeSpecification() {
        throw new IllegalStateException("Utility class");
    }

    public static Specification<RecipeEntity> getRecipeByCriteria(RecipeFilter filter) {
        List<Predicate> predicates = new ArrayList<>();
        return (root, query, criteriaBuilder) -> {
            if (filter.getIsVeg() != null) {
                predicates.add(criteriaBuilder
                        .equal(root.<Boolean>get("isVeg"), filter.getIsVeg()));
            }
            if (filter.getServings() != null) {
                predicates.add(criteriaBuilder
                        .ge(root.<Integer>get("servings"), filter.getServings()));
            }
            if (filter.getInstructions() != null) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("instructions")),
                        "%" + filter.getInstructions().toLowerCase() + "%"));
            }
            if (filter.getIncludeIngredient() != null) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("ingredients")), "%" +
                        filter.getIncludeIngredient().toLowerCase() + "%"));
            }
            if (filter.getExcludeIngredient() != null) {
                predicates.add(criteriaBuilder
                        .notLike(criteriaBuilder.lower(root.get("ingredients")),
                                "%" + filter.getExcludeIngredient().toLowerCase() + "%"));
            }
            //System.out.println("predicates" + predicates.toString());
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
