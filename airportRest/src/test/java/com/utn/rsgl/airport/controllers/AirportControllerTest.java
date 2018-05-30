package com.utn.rsgl.airport.controllers;

import com.utn.rsgl.airport.models.Airport;
import com.utn.rsgl.airport.repositories.AirportRepository;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.ArrayList;
import java.util.List;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class AirportControllerTest {
 /*   @Mock
    AirportRepository airportRepository;
    AirportController airportController;

    @BeforeClass
    public void initAmbient(){
        MockitoAnnotations.initMocks(this);
        airportController.setAirportRepository(airportRepository);
    }

    @Test
    public void saveAnAirportTest(){
        List<Airport> airports = new ArrayList<>();
        Airport airport = new Airport();
        String aiportName;
        String aiportIATACode;
        String cityName;
        String cityIATACode;
        String stateName;
        String countryName;
        String countryISO3Code;

        airportController.save();
        verify(airportRepository).save(airport);
        when(airportRepository.findAll()).thenReturn(airports);
    }
*/
}
