package throne.springreacto.recipe.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import throne.springreacto.recipe.domain.Recipe;
import throne.springreacto.recipe.services.RecipeService;

@Controller
public class RecipeController {
    private final RecipeService recipeService;
    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }
    @RequestMapping(value = "/recipe/{recipe_id}", method = RequestMethod.GET)
    public String getRecipeById(@PathVariable("recipe_id") Long id, Model model){
        Recipe dfa = recipeService.getById(id);
        model.addAttribute("recipe", recipeService.getById(id));
        return "recipe/show";
    }
}
