package throne.springreacto.recipe.controllers;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import throne.springreacto.recipe.commands.IngredientCommand;
import throne.springreacto.recipe.commands.RecipeCommand;
import throne.springreacto.recipe.commands.UnitOfMeasureCommand;
import throne.springreacto.recipe.services.IngredientService;
import throne.springreacto.recipe.services.RecipeService;
import throne.springreacto.recipe.services.UnitOfMeasureServiceImp;

import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class IngredientControllerTest {
    public static final Long RECIPE_ID = 2L;
    @Mock
    RecipeService recipeService;
    @Mock
    IngredientService ingredientService;
    @Mock
    UnitOfMeasureServiceImp unitOfMeasureService;
    MockMvc mockMvc;

    IngredientController sut;
    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        sut = new IngredientController(recipeService, ingredientService, unitOfMeasureService);

        mockMvc = MockMvcBuilders.standaloneSetup(sut).build();
    }

    @Test
    public void testGetIngredients() throws Exception {
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
        mockMvc.perform(get("/recipe/2/ingredient/1/show"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(model().attributeExists("ingredient"))
                .andExpect(view().name("recipe/ingredient/show"));
    }

    @Test
    public void testUpdateIngredient() throws Exception {

        //given
        UnitOfMeasureCommand uomOne = new UnitOfMeasureCommand();
        uomOne.setId(1L);
        uomOne.setDescription("description one");

        UnitOfMeasureCommand uomTwo = new UnitOfMeasureCommand();
        uomTwo.setId(2L);
        uomTwo.setDescription("description two");
        Set<UnitOfMeasureCommand> uomSet = Set.of(uomOne, uomTwo);


        //when
        when(unitOfMeasureService.getUnitOfMeasureList()).thenReturn(uomSet);
        when(ingredientService.findByRecipeIdByIngredientId(anyLong(), anyLong())).thenReturn(new IngredientCommand());
        mockMvc.perform(get("/recipe/1/ingredient/2/update"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(model().attribute("unitOfMeasureList", hasSize(2)))
                .andExpect(model().attributeExists("ingredient"))
        .andExpect(view().name("/recipe/ingredient/ingredientform"));
    }

    @Test
    public void testSaveOrUpdate() throws Exception {
        //given
        IngredientCommand command = new IngredientCommand();
        command.setId(3L);
        command.setRecipeId(2L);

        //when
        when(ingredientService.saveIngredientCommand(any(), anyLong())).thenReturn(command);

        //then
        mockMvc.perform(post("/recipe/2/ingredient")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("id", "")
                .param("description", "some string")
        )
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/recipe/2/ingredient/3/show"));

    }

    @Test
    public void testNewIngredientForm() throws Exception {
        //given
        RecipeCommand recipeCommand = new RecipeCommand();
        recipeCommand.setId(1L);

        //when
        when(recipeService.findCommandById(anyLong())).thenReturn(recipeCommand);
        when(unitOfMeasureService.getUnitOfMeasureList()).thenReturn(new HashSet<>());

        //then
        mockMvc.perform(get("/recipe/1/ingredient/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/ingredient/ingredientform"))
                .andExpect(model().attributeExists("ingredient"))
                .andExpect(model().attributeExists("unitOfMeasureList"));

        verify(recipeService, times(1)).findCommandById(anyLong());

    }

    @Test
    public void testDeleteIngredient() throws Exception {

        //then
        mockMvc.perform(get("/recipe/2/ingredient/3/delete")
        )
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/recipe/2/ingredients"));

        verify(ingredientService, times(1)).deleteById(anyLong(), anyLong());

    }
}