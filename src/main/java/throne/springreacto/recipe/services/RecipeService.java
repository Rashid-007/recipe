package throne.springreacto.recipe.services;

import throne.springreacto.recipe.domain.Recipe;

import java.util.Set;

public interface RecipeService {
    Set<Recipe> getRecipes();
}
