package throne.springreacto.recipe.controllers;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import throne.springreacto.recipe.domain.Recipe;
import throne.springreacto.recipe.services.RecipeService;

import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class RecipeControllerTest {
    @Mock
    RecipeService recipeService;
    @InjectMocks
    RecipeController sut;

    MockMvc mockMvc;
    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(sut).build();
    }

    @Test
    public void getRecipeByIdStatusOk() throws Exception{
        Recipe returnedRecipe = new Recipe();
        returnedRecipe.setId(1L);

        when(recipeService.getById(anyLong())).thenReturn(returnedRecipe);

        mockMvc.perform(get("/recipe/1")).andExpect(status().is(200))
                .andExpect(model().attributeExists("recipe"))
                .andExpect(model().attribute("recipe", hasProperty("id", notNullValue())))
                .andExpect(view().name("recipe/show"));
    }
}