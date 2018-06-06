package com.utn.rsgl.airport.services;

import com.utn.rsgl.airport.exceptions.DataAlreadyExistsException;
import com.utn.rsgl.airport.models.Country;
import com.utn.rsgl.airport.repositories.AirportRepository;
import com.utn.rsgl.airport.repositories.CountryRepository;
import com.utn.rsgl.airport.service.CountryService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class CountryServiceTest {

    @Mock
    CountryRepository countryRepository;
    //@Mock
    //AirportRepository airportRepository;
    CountryService countryService;

    @Before
    public void myTests(){
        MockitoAnnotations.initMocks(this);
        countryService = new CountryService();
    }

    @Test
    public void saveTestFirstBranch() {
        Boolean catching = false;
        when(countryRepository.findCountryByIsoCode3("MDQ")).thenReturn(new Country("Argentina", "MDQ"));
        try {
            countryService.save("Brasil", "MDQ");
            verify(countryRepository).findCountryByIsoCode3("MDQ");
        }catch(Exception e) {
            catching = true;
            Assert.assertTrue(e instanceof Exception);
        }
        Assert.assertTrue(catching);
    }

    @Test
    public void saveTestSecondBranch(){
        List<Country> repository = new ArrayList<>();
        Country anCountry = new Country("Argentina", "ARG");
        when(countryRepository.save(anCountry));
        repository.add(anCountry);
        try {
            countryService.save("Argentina", "ARG");
            verify(countryRepository).save(anCountry);
            Assert.assertEquals("Argentina", repository.get(0).getName());
            Assert.assertEquals("ARG", repository.get(0).getIsoCode3());
        } catch (DataAlreadyExistsException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void updateExceptionTest(){
        Country aCountry = new Country("Argentina", "ARG");
        when(countryRepository.findCountryByIsoCode3("ARG")).thenReturn(new Country("Argentina", "ARG"));
    }

    @Test
    public void removeTest(){

    }

    @Test
    public void findCountryByIsoCode(){

    }
}
