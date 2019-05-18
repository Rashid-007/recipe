package throne.springreacto.recipe.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import throne.springreacto.recipe.commands.RecipeCommand;
import throne.springreacto.recipe.services.RecipeService;

@Controller
public class RecipeController {
    private final RecipeService recipeService;
    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @RequestMapping(value = "/recipe/{recipe_id}", method = RequestMethod.GET)
    public String getRecipeById(@PathVariable("recipe_id") Long id, Model model){
        model.addAttribute("recipe", recipeService.getById(id));
        return "recipe/show";
    }

    @RequestMapping("/recipe/new")
    @GetMapping
    public String requesRecipeForm(Model model){
        model.addAttribute("recipe", new RecipeCommand());

        return "recipe/recipeform";
    }
    @PostMapping
    @RequestMapping("recipe")
    public String saveUpdateRecipe(@ModelAttribute RecipeCommand recipeCommand){
        RecipeCommand savedRecipeCommand = recipeService.saveRecipeCommand(recipeCommand);

        return "redirect:/recipe/" + savedRecipeCommand.getId();

    }
    //@PutMapping
    @GetMapping
    @RequestMapping(value = "/recipe/{id}/update")
    public String updateRecipe(@PathVariable("id") Long id, Model model){
        RecipeCommand commandById = recipeService.findCommandById(id);
        model.addAttribute("recipe", commandById);
        return "recipe/recipeform";
    }
    @GetMapping
    @RequestMapping(value = "/recipe/{recipe_id}/delete")
    public String deleteRecipe(@PathVariable("recipe_id") Long id){
        recipeService.deleteById(id);

        return "redirect:/";
    }
}
