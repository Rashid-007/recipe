package throne.springreacto.recipe.services;

import org.springframework.stereotype.Service;
import throne.springreacto.recipe.commands.IngredientCommand;
import throne.springreacto.recipe.converters.IngredientToIngredientCommand;
import throne.springreacto.recipe.domain.Recipe;
import throne.springreacto.recipe.repositories.RecipeRepository;

import java.util.Optional;

@Service
public class IngredientServiceImp implements IngredientService {
    private final RecipeRepository recipeRepository;
    private final IngredientToIngredientCommand ingredientToIngredientCommand;


    public IngredientServiceImp(RecipeRepository recipeRepository, IngredientToIngredientCommand ingredientToIngredientCommand) {
        this.recipeRepository = recipeRepository;
        this.ingredientToIngredientCommand = ingredientToIngredientCommand;
    }

    @Override
    public IngredientCommand findByRecipeIdByIngredientId(Long recipeId, Long ingredientId) {

        Optional<Recipe> recipeOptional = recipeRepository.findById(recipeId);
        Recipe recipe = recipeOptional
                .orElseThrow(() -> new IllegalArgumentException("There is no recipe with this id " + recipeId));

        Optional<IngredientCommand> ingredientCommandOptional = recipe.getIngredients().stream()
                .filter(ingredient -> ingredient.getId().equals(ingredientId))
                .map(ingredient -> ingredientToIngredientCommand.convert(ingredient)).findFirst();

        IngredientCommand ingredientCommand = ingredientCommandOptional
                .orElseThrow(() -> new IllegalArgumentException("There is no ingredient item with this id " + ingredientId));

        return ingredientCommand;
    }
}
