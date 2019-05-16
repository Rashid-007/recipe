package throne.springreacto.recipe.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import throne.springreacto.recipe.commands.RecipeCommand;
import throne.springreacto.recipe.converters.RecipeCommandToRecipe;
import throne.springreacto.recipe.converters.RecipeToRecipeCommand;
import throne.springreacto.recipe.domain.Recipe;
import throne.springreacto.recipe.repositories.RecipeRepository;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
@Slf4j
@Service
public class RecipeServiceImp implements RecipeService {
    private final RecipeRepository recipeRepository;
    private final RecipeCommandToRecipe recipeCommandToRecipe;
    private final RecipeToRecipeCommand recipeToRecipeCommand;

    public RecipeServiceImp(RecipeRepository recipeRepository, RecipeCommandToRecipe recipeCommandToRecipe, RecipeToRecipeCommand recipeToRecipeCommand) {
        this.recipeRepository = recipeRepository;
        this.recipeCommandToRecipe = recipeCommandToRecipe;
        this.recipeToRecipeCommand = recipeToRecipeCommand;
    }

    @Override
    public Set<Recipe> getRecipes() {
        Set<Recipe> recipes = new HashSet<>();
        recipeRepository.findAll().forEach(recipes::add);
        return recipes;
    }

    @Override
    public Recipe getById(Long id) {
        Optional<Recipe> foundById = recipeRepository.findById(id);
        return foundById.orElseThrow(() -> new RuntimeException("Recipe not found!"));
    }

    @Override
    @Transactional
    public RecipeCommand saveRecipeCommand(RecipeCommand recipeCommand) {
        Recipe savedRecipe = recipeRepository.save(recipeCommandToRecipe.convert(recipeCommand));
        log.debug("saved RecipeId" + savedRecipe.getId());
        RecipeCommand savedRecipeCommand = recipeToRecipeCommand.convert(savedRecipe);
        return savedRecipeCommand;
    }
}
