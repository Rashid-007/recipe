package throne.springreacto.recipe.services;

import throne.springreacto.recipe.commands.UnitOfMeasureCommand;

import java.util.Set;

public interface UnitOfMeasureService {

    Set<UnitOfMeasureCommand> getUnitOfMeasureList();
}
