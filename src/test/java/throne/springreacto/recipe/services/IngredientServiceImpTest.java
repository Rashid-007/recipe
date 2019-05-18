package throne.springreacto.recipe.services;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import throne.springreacto.recipe.commands.IngredientCommand;
import throne.springreacto.recipe.converters.IngredientToIngredientCommand;
import throne.springreacto.recipe.domain.Ingredient;
import throne.springreacto.recipe.domain.Recipe;
import throne.springreacto.recipe.repositories.RecipeRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

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
    IngredientToIngredientCommand ingredientToIngredientCommand;
    IngredientServiceImp sut;
    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
        sut = new IngredientServiceImp(recipeRepository, ingredientToIngredientCommand);
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
        recipe.setIngredients(new HashSet<>(List.of(ingredientOne, ingredientTwo)));

        IngredientCommand ingredientCommand = new IngredientCommand();
        ingredientCommand.setId(CMD_ID);
        ingredientCommand.setRecipeId(RECIPE_ID);

        when(recipeRepository.findById(any())).thenReturn(Optional.of(recipe));
        when(ingredientToIngredientCommand.convert(any())).thenReturn(ingredientCommand);

        //when
        IngredientCommand result = sut.findByRecipeIdByIngredientId(RECIPE_ID, CMD_ID);

        //then
        assertEquals(RECIPE_ID, result.getRecipeId());
        assertEquals(CMD_ID, result.getId());
        verify(recipeRepository, times(1)).findById(RECIPE_ID);
        verify(ingredientToIngredientCommand, times(1)).convert(ingredientTwo);


    }
}