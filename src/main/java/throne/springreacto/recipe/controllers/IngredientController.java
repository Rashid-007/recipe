package throne.springreacto.recipe.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import throne.springreacto.recipe.services.IngredientService;
import throne.springreacto.recipe.services.RecipeService;

@Slf4j
@Controller
public class IngredientController {
    private final RecipeService recipeService;
    private final IngredientService ingredientService;

    public IngredientController(RecipeService recipeService, IngredientService ingredientService) {
        this.recipeService = recipeService;
        this.ingredientService = ingredientService;
    }

    @GetMapping
    @RequestMapping(value = "/recipe/{recipe_id}/ingredients")
    public String getRecipeIngredients(@PathVariable("recipe_id") Long recipeId, Model model) {
        log.debug("Getting ingredient list for recipe id {}", recipeId);
        model.addAttribute("recipe", recipeService.findCommandById(recipeId));
        return "recipe/ingredient/list";
    }
    @GetMapping
    @RequestMapping(value = "/recipe/{recipe_id}/ingredients/{ingredient_id}/show")
    public String showIngredient(@PathVariable("recipe_id") Long recipeId, @PathVariable("ingredient_id") Long ingredientId,
                                 Model model){
        model.addAttribute("ingredient", ingredientService.findByRecipeIdByIngredientId(recipeId, ingredientId));
        return "recipe/ingredient/show";
    }

}
