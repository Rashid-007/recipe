package throne.springreacto.recipe.services;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import throne.springreacto.recipe.commands.IngredientCommand;
import throne.springreacto.recipe.converters.IngredientCommandToIngredient;
import throne.springreacto.recipe.converters.IngredientToIngredientCommand;
import throne.springreacto.recipe.converters.UnitOfMeasureCommandToUnitOfMeasure;
import throne.springreacto.recipe.converters.UnitOfMeasureToUnitOfMeasureCommand;
import throne.springreacto.recipe.domain.Ingredient;
import throne.springreacto.recipe.domain.Recipe;
import throne.springreacto.recipe.repositories.RecipeRepository;
import throne.springreacto.recipe.repositories.UnitOfMeasureRepository;


import java.util.Optional;
import java.util.Set;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class IngredientServiceImpTest {
    public static final Long RECIPE_ID = 1L;
    public static final Long CMD_ID = 2L;
    public static final String RESULT_ING_DESC = "description";
    @Mock
    RecipeRepository recipeRepository;
    @Mock
    UnitOfMeasureRepository unitOfMeasureRepository;

    private final IngredientCommandToIngredient ingredientCommandToIngredient;
    private final IngredientToIngredientCommand ingredientToIngredientCommand;

    IngredientServiceImp sut;

    public IngredientServiceImpTest() {
        this.ingredientCommandToIngredient = new IngredientCommandToIngredient(new UnitOfMeasureCommandToUnitOfMeasure());
        this.ingredientToIngredientCommand = new IngredientToIngredientCommand(new UnitOfMeasureToUnitOfMeasureCommand());
    }

    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
        sut = new IngredientServiceImp(recipeRepository, ingredientToIngredientCommand, ingredientCommandToIngredient,
                unitOfMeasureRepository);
    }

    @Test
    public void findByRecipeIdByIngredientId() {

        //given
        Recipe recipe = new Recipe();
        recipe.setId(RECIPE_ID);

        Ingredient ingredientOne = new Ingredient();
        ingredientOne.setId(1L);
        Ingredient ingredientTwo = new Ingredient();
        ingredientTwo.setDescription(RESULT_ING_DESC);
        ingredientTwo.setId(2L);

        recipe.setIngredients(Set.of(ingredientOne, ingredientTwo)); //Java 9 addition

        IngredientCommand ingredientCommand = new IngredientCommand();
        ingredientCommand.setId(CMD_ID);
        ingredientCommand.setRecipeId(RECIPE_ID);

        when(recipeRepository.findById(any())).thenReturn(Optional.of(recipe));

        //when
        IngredientCommand result = sut.findByRecipeIdByIngredientId(RECIPE_ID, CMD_ID);

        //then
        assertEquals(CMD_ID, result.getId());
        verify(recipeRepository, times(1)).findById(RECIPE_ID);
    }
    @Test
    public void testSaveIngredientCommand() {
        //given
        IngredientCommand command = new IngredientCommand();
        command.setId(3L);
        command.setRecipeId(2L);

        Optional<Recipe> recipeOptional = Optional.of(new Recipe());

        Recipe savedRecipe = new Recipe();
        savedRecipe.addIngredient(new Ingredient());
        savedRecipe.getIngredients().iterator().next().setId(3L);

        when(recipeRepository.findById(anyLong())).thenReturn(recipeOptional);
        when(recipeRepository.save(any())).thenReturn(savedRecipe);

        //when
        IngredientCommand savedCommand = sut.saveIngredientCommand(command, anyLong());

        //then
        assertEquals(Long.valueOf(3L), savedCommand.getId());
        verify(recipeRepository, times(1)).findById(anyLong());
        verify(recipeRepository, times(1)).save(any(Recipe.class));

    }
    @Test
    public void testDeleteById() {
        //given
        Recipe recipe = new Recipe();
        Ingredient ingredient = new Ingredient();
        ingredient.setId(3L);
        recipe.addIngredient(ingredient);
        ingredient.setRecipe(recipe);
        Optional<Recipe> recipeOptional = Optional.of(recipe);

        when(recipeRepository.findById(anyLong())).thenReturn(recipeOptional);

        //when
        sut.deleteById(1L, 3L);

        //then
        verify(recipeRepository, times(1)).findById(anyLong());
        verify(recipeRepository, times(1)).save(any(Recipe.class));
    }
}