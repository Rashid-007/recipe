package throne.springreacto.recipe.services;

import throne.springreacto.recipe.commands.IngredientCommand;

public interface IngredientService {
    IngredientCommand findByRecipeIdByIngredientId(Long recipeId, Long id);

    IngredientCommand saveIngredientCommand(IngredientCommand ingredientCommand, Long recipeId);

    void deleteById(Long recipeId, Long idToDelete);
}
