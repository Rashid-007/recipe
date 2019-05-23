package throne.springreacto.recipe.controllers;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.pl.REGON;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import throne.springreacto.recipe.commands.IngredientCommand;
import throne.springreacto.recipe.commands.RecipeCommand;
import throne.springreacto.recipe.commands.UnitOfMeasureCommand;
import throne.springreacto.recipe.domain.Difficulty;
import throne.springreacto.recipe.services.IngredientService;
import throne.springreacto.recipe.services.RecipeService;
import throne.springreacto.recipe.services.UnitOfMeasureService;

@Slf4j
@Controller
public class IngredientController {
    private final RecipeService recipeService;
    private final IngredientService ingredientService;
    private final UnitOfMeasureService unitOfMeasureService;

    public IngredientController(RecipeService recipeService, IngredientService ingredientService,
                                UnitOfMeasureService unitOfMeasureService) {
        this.recipeService = recipeService;
        this.ingredientService = ingredientService;
        this.unitOfMeasureService = unitOfMeasureService;
    }

    @GetMapping(value = "/recipe/{recipe_id}/ingredients")
    public String getRecipeIngredients(@PathVariable("recipe_id") Long recipeId, Model model) {
        log.debug("Getting ingredient list for recipe id {}", recipeId);
        model.addAttribute("recipe", recipeService.findCommandById(recipeId));
        return "recipe/ingredient/list";
    }
    @GetMapping(value = "/recipe/{recipe_id}/ingredient/{ingredient_id}/show")
    public String showIngredient(@PathVariable("recipe_id") Long recipeId, @PathVariable("ingredient_id") Long ingredientId,
                                 Model model){
        model.addAttribute("ingredient", ingredientService.findByRecipeIdByIngredientId(recipeId, ingredientId));
        return "recipe/ingredient/show";
    }

    @GetMapping(value = "/recipe/{recipe_id}/ingredient/{ingredient_id}/update")
    public String updateIngredient(@PathVariable("recipe_id") Long recipeId,
                                   @PathVariable("ingredient_id") Long ingredientId, Model model){
        IngredientCommand test = ingredientService.findByRecipeIdByIngredientId(recipeId, ingredientId);
        model.addAttribute("ingredient", ingredientService.findByRecipeIdByIngredientId(recipeId, ingredientId));
        model.addAttribute("unitOfMeasureList", unitOfMeasureService.getUnitOfMeasureList());
        return "/recipe/ingredient/ingredientform";
    }

    @PostMapping(value = "recipe/{recipe_id}/ingredient")
    public String saveOrUpdate(@ModelAttribute IngredientCommand ingredient, @PathVariable("recipe_id") Long recipeId){
        IngredientCommand savedCommand = ingredientService.saveIngredientCommand(ingredient, recipeId);
        log.debug("saved recipe id {}", savedCommand.getRecipeId());
        log.debug("saved ingredient id {}", savedCommand.getId());
        return "redirect:/recipe/" + savedCommand.getRecipeId() + "/ingredient/" + savedCommand.getId() + "/show";
    }

    @GetMapping("recipe/{recipeId}/ingredient/new")
    public String newIngredient(@PathVariable String recipeId, Model model){

        //make sure we have a good id value
        RecipeCommand recipeCommand = recipeService.findCommandById(Long.valueOf(recipeId));
        //todo raise exception if null

        //need to return back parent id for hidden form property
        IngredientCommand ingredientCommand = new IngredientCommand();
        ingredientCommand.setRecipeId(Long.valueOf(recipeId));
        model.addAttribute("ingredient", ingredientCommand);

        //init uom
        ingredientCommand.setUnitOfMeasure(new UnitOfMeasureCommand());

        model.addAttribute("unitOfMeasureList",  unitOfMeasureService.getUnitOfMeasureList());

        return "recipe/ingredient/ingredientform";
    }

    @GetMapping("recipe/{recipeId}/ingredient/{id}/delete")
    public String deleteIngredient(@PathVariable String recipeId,
                                   @PathVariable String id){

        log.debug("deleting ingredient id:" + id);
        ingredientService.deleteById(Long.valueOf(recipeId), Long.valueOf(id));

        return "redirect:/recipe/" + recipeId + "/ingredients";
    }

}
