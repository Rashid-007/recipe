package throne.springreacto.recipe.converters;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import throne.springreacto.recipe.commands.IngredientCommand;
import throne.springreacto.recipe.commands.UnitOfMeasureCommand;
import throne.springreacto.recipe.domain.Ingredient;
import throne.springreacto.recipe.domain.UnitOfMeasure;

import java.math.BigDecimal;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class IngredientCommandToIngredientTest {
    public static final Long IG_ID = 1L;
    public static final BigDecimal AMOUNT = BigDecimal.valueOf(1);
    public static final String DESCRIPTION = "description";
    public static final Long UOM_ID = 1L;
    public static final String UOM_DESCRIPTION = "Uom description";

    IngredientCommandToIngredient sut;

    @Before
    public void setUp(){
        sut = new IngredientCommandToIngredient(new UnitOfMeasureCommandToUnitOfMeasure());
    }

    @Test
    public void testNullParameter() {
        assertNull(sut.convert(null));
    }

    @Test
    public void testEmptyObject() {
        assertNotNull(sut.convert(new IngredientCommand()));
    }

    @Test
    public void convert() {
        //given
        IngredientCommand ingredientCommand = new IngredientCommand();
        ingredientCommand.setId(IG_ID);
        ingredientCommand.setAmount(AMOUNT);
        ingredientCommand.setDescription(DESCRIPTION);
        UnitOfMeasureCommand unitOfMeasureCommand = new UnitOfMeasureCommand();
        unitOfMeasureCommand.setId(UOM_ID);
        unitOfMeasureCommand.setDescription(UOM_DESCRIPTION);
        ingredientCommand.setUnitOfMeasureCommand(unitOfMeasureCommand);

        //when
        Ingredient ingredient = sut.convert(ingredientCommand);

        //then
        assertNotNull(ingredient);
        assertEquals(IG_ID, ingredient.getId());
        assertNotNull(ingredient.getUnitOfMeasure());
        assertEquals(UOM_ID, ingredient.getUnitOfMeasure().getId());
    }
}