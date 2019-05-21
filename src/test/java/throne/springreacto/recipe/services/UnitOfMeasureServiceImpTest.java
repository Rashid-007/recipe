package throne.springreacto.recipe.services;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import throne.springreacto.recipe.commands.UnitOfMeasureCommand;
import throne.springreacto.recipe.converters.UnitOfMeasureToUnitOfMeasureCommand;
import throne.springreacto.recipe.domain.UnitOfMeasure;
import throne.springreacto.recipe.repositories.UnitOfMeasureRepository;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class UnitOfMeasureServiceImpTest {
    @Mock
    UnitOfMeasureRepository unitOfMeasureRepository;

    UnitOfMeasureToUnitOfMeasureCommand unitOfMeasureToUnitOfMeasureCommand = new UnitOfMeasureToUnitOfMeasureCommand();

    UnitOfMeasureServiceImp sut;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        sut = new UnitOfMeasureServiceImp(unitOfMeasureRepository, unitOfMeasureToUnitOfMeasureCommand);
    }

    @Test
    public void testGetUnitOfMeasureList() {
        UnitOfMeasure uomOne = new UnitOfMeasure();
        uomOne.setId(1L);
        uomOne.setDescription("desc1");
        UnitOfMeasure uomTwo = new UnitOfMeasure();
        uomTwo.setId(2L);
        uomTwo.setDescription("desc2");
        Set<UnitOfMeasure> uomList = Set.of(uomOne, uomTwo);

        when(unitOfMeasureRepository.findAll()).thenReturn(uomList);

        Set<UnitOfMeasureCommand> uomCommandList = sut.getUnitOfMeasureList();

        assertEquals(2, uomCommandList.size());
        verify(unitOfMeasureRepository, times(1)).findAll();


    }
}