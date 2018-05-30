package com.utn.rsgl.airport.factories;


import com.utn.rsgl.airport.models.Country;
import com.utn.rsgl.airport.models.State;
import com.utn.rsgl.core.shared.dto.CountryDTO;
import com.utn.rsgl.core.shared.dto.StateDTO;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class DtoFactoryTest {

    private static Country country;
    private static State state;
    private static DtoFactory dtoFactory;

    @BeforeClass
    public static void init(){
        country = new Country();
        state = new State();
        country.setName("Alvania");
        country.setISOCode3("ALV");
        country.setId(1);
        state.setName("Maribelius");
        state.setCountry(country);
        dtoFactory = DtoFactory.getInstance();
    }

    @Test
    public void testSingleton(){
        dtoFactory = DtoFactory.getInstance();
        Assert.assertNotNull(dtoFactory);
        Assert.assertTrue(dtoFactory instanceof DtoFactory);
        Assert.assertSame(dtoFactory, DtoFactory.getInstance());
    }

    @Test
    public void countryCreationTest(){

        CountryDTO countryDTO = dtoFactory.getDTOByModel(country, CountryDTO.class);
        Assert.assertNotNull(countryDTO);
        Assert.assertTrue(countryDTO.getName().equals(country.getName()));
        Assert.assertTrue(countryDTO.getISOCode3().equals(country.getISOCode3()));
        Assert.assertEquals(countryDTO, dtoFactory.getDTOByModel(country, CountryDTO.class));
    }

    @Test
    public void stateCreationTest(){
        StateDTO stateDTO = dtoFactory.getDTOByModel(state, StateDTO.class);
    }
}
