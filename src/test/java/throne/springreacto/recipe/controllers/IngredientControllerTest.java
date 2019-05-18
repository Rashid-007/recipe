package throne.springreacto.recipe.controllers;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import throne.springreacto.recipe.commands.IngredientCommand;
import throne.springreacto.recipe.commands.RecipeCommand;
import throne.springreacto.recipe.services.IngredientService;
import throne.springreacto.recipe.services.RecipeService;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class IngredientControllerTest {
    public static final Long RECIPE_ID = 2L;
    @Mock
    RecipeService recipeService;
    @Mock
    IngredientService ingredientService;
    MockMvc mockMvc;

    IngredientController sut;
    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        sut = new IngredientController(recipeService, ingredientService);

        mockMvc = MockMvcBuilders.standaloneSetup(sut).build();
    }

    @Test
    public void getIngredients() throws Exception {
        RecipeCommand recipeCommand = new RecipeCommand();
        recipeCommand.setId(RECIPE_ID);

        when(recipeService.findCommandById(RECIPE_ID)).thenReturn(recipeCommand);

        mockMvc.perform(get("/recipe/2/ingredients"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(model().attributeExists("recipe"))
                .andExpect(view().name("recipe/ingredient/list"));

    }

    @Test
    public void testShowIngredient() throws Exception {
        IngredientCommand ingredientCommand = new IngredientCommand();
        ingredientCommand.setRecipeId(2L);
        ingredientCommand.setId(1L);
        when(ingredientService.findByRecipeIdByIngredientId(anyLong(), anyLong())).thenReturn(ingredientCommand);
        mockMvc.perform(get("/recipe/2/ingredients/1/show"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(model().attributeExists("ingredient"))
                .andExpect(view().name("recipe/ingredient/show"));
    }
}