package throne.springreacto.recipe.services;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import throne.springreacto.recipe.converters.RecipeCommandToRecipe;
import throne.springreacto.recipe.converters.RecipeToRecipeCommand;
import throne.springreacto.recipe.domain.Recipe;
import throne.springreacto.recipe.repositories.RecipeRepository;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

public class RecipeServiceImpTest {

    RecipeServiceImp sut;
    @Mock
    RecipeRepository recipeRepository;
    @Mock
    RecipeToRecipeCommand recipeToRecipeCommand;
    @Mock
    RecipeCommandToRecipe recipeCommandToRecipe;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        sut = new RecipeServiceImp(recipeRepository, recipeCommandToRecipe, recipeToRecipeCommand);
    }

    @Test
    public void getRecipeById(){
        Recipe recipeById = new Recipe();
        recipeById.setId(1L);
        when(recipeRepository.findById(anyLong())).thenReturn(Optional.of(recipeById));

        //WHEN
        Recipe recipe = sut.getById(1L);

        verify(recipeRepository).findById(anyLong());
        assertNotNull("Null recipe returned", recipe);
        verify(recipeRepository, never()).findAll();
    }

    @Test
    public void getRecipes() {
        Recipe recipe = new Recipe();
        HashSet recipeData = new HashSet();
        recipeData.add(recipe);

        when(recipeRepository.findAll()).thenReturn(recipeData);

        Set<Recipe> recipes = sut.getRecipes();
        assertEquals(recipes.size(), 1);
        verify(recipeRepository, times(1)).findAll();
    }
}