package com.utn.rsgl.airport.controllers;

import com.utn.rsgl.airport.exceptions.DataAlreadyExistsException;
import com.utn.rsgl.airport.requests.AirportRequest;
import com.utn.rsgl.airport.service.AirportService;
import com.utn.rsgl.core.shared.dto.AirportDTO;
import com.utn.rsgl.core.shared.dto.CityDTO;
import com.utn.rsgl.core.shared.dto.CountryDTO;
import com.utn.rsgl.core.shared.dto.StateDTO;
import javassist.NotFoundException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

public class AirportControllerTest {
    @Mock
    AirportService airportService;

    @Mock
    HttpServletRequest request;

    AirportController airportController;
    AirportDTO airportDto;

    @Before
    public void init(){
        MockitoAnnotations.initMocks(this);
        airportController = new AirportController();
        airportController.setAirportService(airportService);
        initAirportDto();
    }

    private void initAirportDto() {
        airportDto = new AirportDTO();
        CityDTO city = new CityDTO();
        StateDTO state = new StateDTO();
        CountryDTO country = new CountryDTO();
        country.setName("Argentina");
        country.setIsoCode3("ARG");
        state.setName("Provincia de BS AS");
        state.setCountry(country);
        city.setName("Buenos Aires");
        city.setIataCode("BSA");
        city.setState(state);
        airportDto.setName("Aerolineas argentinas");
        airportDto.setIataCode("LAN");
        airportDto.setCity(city);
    }

    @Test
    public void saveNotFoundExceptionTest(){
        AirportRequest airportRequest = new AirportRequest("Aerolineas Argentinas", "LAN", "BSA");
        ResponseEntity myEntity = null;
        try {
            doThrow(new NotFoundException("error")).when(airportService).save(airportRequest);
            myEntity = airportController.save(airportRequest);
            verify(airportService).save(airportRequest);
        } catch (Exception e) {}
        Assert.assertNotNull(myEntity);
        Assert.assertEquals(myEntity.getStatusCode(), HttpStatus.NOT_FOUND);
    }

    @Test
    public void saveDataAlreadyExistsExceptionTest(){
        AirportRequest airportRequest = new AirportRequest("Aerolineas Argentinas", "LAN", "BSA");
        ResponseEntity myEntity = null;
        try {
            doThrow(new DataAlreadyExistsException("error")).when(airportService).save(airportRequest);
            myEntity = airportController.save(airportRequest);
            verify(airportService).save(airportRequest);
        } catch (Exception e) {}
        Assert.assertNotNull(myEntity);
        Assert.assertEquals(myEntity.getStatusCode(), HttpStatus.IM_USED);
    }

    @Test
    public void saveExceptionTest(){
        AirportRequest airportRequest = new AirportRequest("Aerolineas Argentinas", "LAN", "BSA");
        ResponseEntity myEntity = null;
        try {
            doThrow(new Exception("error")).when(airportService).save(airportRequest);
            myEntity = airportController.save(airportRequest);
            verify(airportService).save(airportRequest);
        } catch (Exception e) {}
        Assert.assertNotNull(myEntity);
        Assert.assertEquals(myEntity.getStatusCode(), HttpStatus.INTERNAL_SERVER_ERROR);

    }

    @Test
    public void saveAnAirportTest(){
        AirportRequest airportRequest = new AirportRequest("Aerolineas Argentinas", "LAN", "BSA");
        ResponseEntity myEntity = null;

        try {
            doNothing().when(airportService).save(airportRequest);
            myEntity = airportController.save(airportRequest);
            verify(airportService).save(airportRequest);
        } catch (Exception e) {}

        Assert.assertNotNull(myEntity);
        Assert.assertEquals(myEntity.getStatusCode(), HttpStatus.CREATED);
    }

    @Test
    public void updateNotFoundExceptionTest(){
        AirportRequest airportRequest = new AirportRequest("Aerolineas Argentinas", "LAN", "BSA");
        ResponseEntity myEntity = null;

        try {
            doThrow(new NotFoundException("error")).when(airportService).update(airportRequest);
            myEntity = airportController.update(airportRequest);
            verify(airportService).update(airportRequest);
        } catch (Exception e) {}

        Assert.assertNotNull(myEntity);
        Assert.assertEquals(myEntity.getStatusCode(), HttpStatus.NOT_FOUND);
    }

    @Test
    public void updateExceptionTest(){
        AirportRequest airportRequest = new AirportRequest("Aerolineas Argentinas", "LAN", "BSA");
        ResponseEntity myEntity = null;

        try {
            doThrow(new Exception("error")).when(airportService).update(airportRequest);
            myEntity = airportController.update(airportRequest);
            verify(airportService).update(airportRequest);
        } catch (Exception e) {}

        Assert.assertNotNull(myEntity);
        Assert.assertEquals(myEntity.getStatusCode(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Test
    public void updateAirportTest(){
        AirportRequest airportRequest = new AirportRequest("Aerolineas Argentinas", "LAN", "BSA");
        ResponseEntity myEntity = null;

        try {
            doNothing().when(airportService).update(airportRequest);
            myEntity = airportController.update(airportRequest);
            verify(airportService).update(airportRequest);
        } catch (Exception e) {}

        Assert.assertNotNull(myEntity);
        Assert.assertEquals(myEntity.getStatusCode(), HttpStatus.OK);
    }

    @Test
    public void deleteAirportTest(){
        AirportRequest airportRequest = new AirportRequest("Aerolineas Argentinas", "LAN", "BSA");
        ResponseEntity myEntity = null;

        try {
            doNothing().when(airportService).remove(airportRequest);
            myEntity = airportController.delete(airportRequest);
            verify(airportService).remove(airportRequest);
        } catch (Exception e) {}

        Assert.assertNotNull(myEntity);
        Assert.assertEquals(myEntity.getStatusCode(), HttpStatus.OK);
    }

    @Test
    public void deleteNotFoundExceptionTest(){
        AirportRequest airportRequest = new AirportRequest("Aerolineas Argentinas", "LAN", "BSA");
        ResponseEntity myEntity = null;

        try {
            doThrow(new NotFoundException("error")).when(airportService).remove(airportRequest);
            myEntity = airportController.delete(airportRequest);
            verify(airportService).remove(airportRequest);
        } catch (Exception e) {}

        Assert.assertNotNull(myEntity);
        Assert.assertEquals(myEntity.getStatusCode(), HttpStatus.NOT_FOUND);
    }

    @Test
    public void listAllAirportsTest(){

        List<AirportDTO> obtainedAirportDtos = new ArrayList<>();
        List<AirportDTO> airportDtos = new ArrayList<>();
        airportDtos.add(airportDto);
        obtainedAirportDtos.add(airportDto);
        ResponseEntity response = null;

        when(request.getParameter("iataCode")).thenReturn(null);
        when(airportService.listAll()).thenReturn(obtainedAirportDtos);
        response = airportController.list(request);
        verify(airportService).listAll();

        Assert.assertNotNull(response);
        obtainedAirportDtos = (List<AirportDTO>) response.getBody();
        Assert.assertNotNull(obtainedAirportDtos);
        Assert.assertEquals(airportDtos.size(), obtainedAirportDtos.size());
        Assert.assertArrayEquals(airportDtos.toArray(), obtainedAirportDtos.toArray());
    }

    @Test
    public void listOneAirportTest(){
        List<AirportDTO> obtainedAirportDtos = new ArrayList<>();
        List<AirportDTO> airportDtos = new ArrayList<>();
        airportDtos.add(airportDto);
        obtainedAirportDtos.add(airportDto);
        ResponseEntity response = null;

        when(request.getParameter("iataCode")).thenReturn("BSA");
        try {
            when(airportService.listByIata("BSA")).thenReturn(airportDto);
            response = airportController.list(request);
            verify(request, times(2)).getParameter("iataCode");
            verify(airportService).listByIata(request.getParameter("iataCode"));
        } catch (Exception e) {}

        Assert.assertNotNull(response);
        obtainedAirportDtos = (List<AirportDTO>) response.getBody();
        Assert.assertNotNull(obtainedAirportDtos);
        Assert.assertEquals(1, obtainedAirportDtos.size());
        Assert.assertEquals(airportDtos.size(), obtainedAirportDtos.size());
        Assert.assertArrayEquals(airportDtos.toArray(), obtainedAirportDtos.toArray());
    }

    @Test
    public void listExceptionTest(){
        ResponseEntity response = null;
        try{
            doThrow(new Exception("error")).when(airportService).listByIata("LAN");
            when(request.getParameter("iataCode")).thenReturn("LAN");
            response = airportController.list(request);
            verify(request, times(2)).getParameter("iataCode");
            verify(airportService).listByIata("LAN");
        }catch (Exception e){}

        Assert.assertNotNull(response);
        Assert.assertEquals(response.getStatusCode(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Test
    public void listNotFoundExceptionTest(){
        ResponseEntity response = null;
        try{
            doThrow(new NotFoundException("error")).when(airportService).listByIata("LAN");
            when(request.getParameter("iataCode")).thenReturn("LAN");
            response = airportController.list(request);
            verify(request, times(2)).getParameter("iataCode");
            verify(airportService).listByIata("LAN");
        }catch (Exception e){}

        Assert.assertNotNull(response);
        Assert.assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);
    }

}
