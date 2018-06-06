package com.utn.rsgl.airport.factories;


import com.utn.rsgl.airport.models.City;
import com.utn.rsgl.airport.models.Country;
import com.utn.rsgl.airport.models.State;
import com.utn.rsgl.core.shared.dto.CityDTO;
import com.utn.rsgl.core.shared.dto.CountryDTO;
import com.utn.rsgl.core.shared.dto.StateDTO;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.validation.constraints.AssertTrue;

public class DtoFactoryTest {

    private static Country country;
    private static State state;
    private static City city;
    private static DtoFactory dtoFactory;

    @BeforeClass
    public static void init(){
        initCountry();
        initState();
        initCity();
        dtoFactory = DtoFactory.getInstance();
    }

    private static void initCity() {
        city = new City();
        city.setIataCode("BSA");
        city.setName("Buenos Aires");
        city.setState(state);
        city.setId(5);
    }

    private static void initState() {
        state = new State();
        state.setName("Maribelius");
        state.setCountry(country);
        state.setId(3);
    }

    private static void initCountry(){
        country = new Country();
        country.setName("Alvania");
        country.setIsoCode3("ALV");
        country.setId(1);
    }

    @Test
    public void testSingleton(){
        dtoFactory = DtoFactory.getInstance();
        Assert.assertNotNull(dtoFactory);
        Assert.assertTrue(dtoFactory instanceof DtoFactory);
        Assert.assertSame(dtoFactory, DtoFactory.getInstance());
    }

    @Test
    public void nullSourceTest(){
        boolean catched = false;
        try {
            Object obj = dtoFactory.getDTOByModel(null, Object.class);
        }catch(IllegalArgumentException e){
            catched = true;
            Assert.assertNotNull(e);
            Assert.assertTrue(e instanceof Exception);
        }
        Assert.assertTrue(catched);
    }

    @Test
    public void nullDestinationTest(){
        boolean catched = false;
        try{
            Object obj = dtoFactory.getDTOByModel(new Object(), null);
        }catch (IllegalArgumentException e){
            catched = true;
            Assert.assertNotNull(e);
            Assert.assertTrue(e instanceof Exception);
        }
        Assert.assertTrue(catched);
    }

    @Test
    public void countryCreationTest(){

        CountryDTO countryDTO = dtoFactory.getDTOByModel(country, CountryDTO.class);
        Assert.assertNotNull(countryDTO);
        Assert.assertTrue(countryDTO.getName().equals(country.getName()));
        Assert.assertTrue(countryDTO.getIsoCode3().equals(country.getIsoCode3()));
        Assert.assertEquals(countryDTO, dtoFactory.getDTOByModel(country, CountryDTO.class));
    }

    @Test
    public void stateCreationTest(){
        StateDTO stateDTO = dtoFactory.getDTOByModel(state, StateDTO.class);
        Assert.assertNotNull(stateDTO);
        Assert.assertEquals(stateDTO.getName(), state.getName());
        Assert.assertEquals(stateDTO.getCountry().getName(), country.getName());
        Assert.assertEquals(stateDTO.getCountry().getIsoCode3(), country.getIsoCode3());
        Assert.assertEquals(stateDTO, dtoFactory.getDTOByModel(state, StateDTO.class));
    }

    @Test
    public void cityCreationTest(){
        CityDTO cityDTO = dtoFactory.getDTOByModel(city, CityDTO.class);
        Assert.assertNotNull(cityDTO);
        Assert.assertEquals(cityDTO.getName(), city.getName());
        Assert.assertEquals(cityDTO.getIataCode(), city.getIataCode());
        Assert.assertEquals(cityDTO.getState().getName(), state.getName());
        Assert.assertEquals(cityDTO.getState().getCountry().getName(), country.getName());
        Assert.assertEquals(cityDTO.getState().getCountry().getIsoCode3(), country.getIsoCode3());
        Assert.assertEquals(cityDTO, dtoFactory.getDTOByModel(city, CityDTO.class));
    }
}
