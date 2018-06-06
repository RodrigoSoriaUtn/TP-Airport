package com.utn.rsgl.airport.services;

import com.utn.rsgl.airport.exceptions.DataAlreadyExistsException;
import com.utn.rsgl.airport.models.Airport;
import com.utn.rsgl.airport.models.City;
import com.utn.rsgl.airport.repositories.AirportRepository;
import com.utn.rsgl.airport.repositories.CityRepository;
import com.utn.rsgl.airport.service.AirportService;
import com.utn.rsgl.core.shared.dto.AirportDTO;
import javassist.NotFoundException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class AirportServiceTest {

    @Mock
    private AirportRepository airportRepository;

    @Mock
    private CityRepository cityRepository;

    private AirportService airportService;

    private String name = "Aerolíneas argentinas";
    private String iataCode = "ARG";
    private String cityIataCode = "MDQ";

    @Before
    public void init(){
        this.airportService = new AirportService();
        MockitoAnnotations.initMocks(this);
        airportService.setAirportRepository(airportRepository);
        airportService.setCityRepository(cityRepository);
    }

    @Test
    public void saveNotFoundExceptionTest(){
        boolean catched = false;
        when(cityRepository.findByIataCode(cityIataCode)).thenReturn(null);
        try {
            airportService.save(name, iataCode,cityIataCode);
            verify(cityRepository).findByIataCode(cityIataCode);
        } catch (Exception e) {
            Assert.assertTrue(e instanceof NotFoundException);
            catched = true;
        }
        Assert.assertTrue(catched);
    }

    @Test
    public void saveDataAlreadyExistExceptionTest(){
        boolean catched = false;
        when(cityRepository.findByIataCode(cityIataCode)).thenReturn(new City(name,cityIataCode));
        when(airportRepository.findAirportByIataCode(iataCode)).thenReturn(new Airport(name,iataCode));
        try {
            airportService.save(name, iataCode,cityIataCode);
            verify(airportRepository).findAirportByIataCode(iataCode);
        } catch (Exception e) {
            Assert.assertTrue(e instanceof DataAlreadyExistsException);
            catched = true;
        }
        Assert.assertTrue(catched);
    }

    @Test
    public void saveTest(){
        List<Airport> airports = new ArrayList<>();
        City city = new City(name,cityIataCode);

        when(airportRepository.findAirportByIataCode(iataCode)).thenReturn(null);
        when(cityRepository.findByIataCode(cityIataCode)).thenReturn(new City(name, cityIataCode));
        try {
            Airport airport = new Airport(name, iataCode);
            airport.setCity(city);
            airportService.save(name,iataCode,cityIataCode);
            verify(airportRepository).save(airport);
            airports.add(airport);
        } catch (NotFoundException e) {
            e.printStackTrace();
        } catch (DataAlreadyExistsException e) {
            e.printStackTrace();
        }
        Assert.assertEquals(airports.get(0).getName(),name);
        Assert.assertEquals(airports.get(0).getIataCode(),iataCode);
        Assert.assertEquals(airports.get(0).getCity(),city);
    }

    @Test
    public void listAirportTest(){

        List<Airport> airports = new ArrayList<>();
        List<AirportDTO> airportDtos = new ArrayList<>();
        List<AirportDTO> repository = new ArrayList<>();

        airports.add(new Airport(name, iataCode));
        airports.add(new Airport("Alvanian CO", "ALV"));
        airports.add(new Airport("Pizzarra SA", "PZR"));
        AirportDTO airportDto1 = new AirportDTO();
        airportDto1.setName("Alvanian CO");
        airportDto1.setIataCode("ALV");
        AirportDTO airportDto2 = new AirportDTO();
        airportDto2.setName("Pizzarra SA");
        airportDto2.setIataCode("PZR");

        airportDtos.add(airportDto1);
        airportDtos.add(airportDto2);

        when(airportRepository.findAll()).thenReturn(airports);
        repository = airportService.listAll();
        verify(airportRepository).findAll();

        for(int i = 0; i < repository.size(); i++){
            Assert.assertEquals(repository.get(i).getName(), airportDtos.get(i).getName());
            Assert.assertEquals(repository.get(i).getIataCode(), airportDtos.get(i).getIataCode());
            //The cities are null.
            Assert.assertEquals(repository.get(i).getCity(), airportDtos.get(i).getCity());
        }

    }

}
