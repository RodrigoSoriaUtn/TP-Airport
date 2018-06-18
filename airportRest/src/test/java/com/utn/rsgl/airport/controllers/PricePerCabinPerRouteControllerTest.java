package com.utn.rsgl.airport.controllers;

import com.utn.rsgl.airport.requests.PricePerCabinPerRouteRequest;
import com.utn.rsgl.airport.service.PricePerCabinPerRouteService;
import javassist.NotFoundException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;

import static org.mockito.Mockito.*;

public class PricePerCabinPerRouteControllerTest {

    @Mock
    private PricePerCabinPerRouteService service;
    @Mock
    private HttpServletRequest httpRequest;

    private PricePerCabinPerRouteController controller;

    @Before
    public void init(){
        controller = new PricePerCabinPerRouteController();
        MockitoAnnotations.initMocks(this);
        controller.setPriceService(service);
    }

    @Test
    public void saveExceptionTest(){
        PricePerCabinPerRouteRequest request = new PricePerCabinPerRouteRequest();
        ResponseEntity myEntity = null;
        try {
            doThrow(new Exception("error")).when(service).save(request);
            myEntity = controller.save(request);
            verify(service).save(request);
        } catch (Exception e) {}
        Assert.assertNotNull(myEntity);
        Assert.assertEquals(myEntity.getStatusCode(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Test
    public void saveIllegalArgumentExceptionTest(){
        PricePerCabinPerRouteRequest request = new PricePerCabinPerRouteRequest();
        ResponseEntity myEntity = null;
        try {
            doThrow(new IllegalArgumentException("error")).when(service).save(request);
            myEntity = controller.save(request);
            verify(service).save(request);
        } catch (Exception e) {}
        Assert.assertNotNull(myEntity);
        Assert.assertEquals(myEntity.getStatusCode(), HttpStatus.BAD_REQUEST);
    }

    @Test
    public void saveTest(){
        PricePerCabinPerRouteRequest request = new PricePerCabinPerRouteRequest();
        ResponseEntity myEntity = null;
        try {
            doNothing().when(service).save(request);
            myEntity = controller.save(request);
            verify(service).save(request);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Assert.assertNotNull(myEntity);
        Assert.assertEquals(myEntity.getStatusCode(), HttpStatus.CREATED);
    }

    @Test
    public void updateTest(){
        PricePerCabinPerRouteRequest request = new PricePerCabinPerRouteRequest();
        ResponseEntity myEntity = null;

        try {
            when(httpRequest.getParameter("routeId")).thenReturn("3");
            doNothing().when(service).update(request, 3L);
            myEntity = controller.update(request, httpRequest);
            verify(httpRequest, times(2)).getParameter("routeId");
            verify(service).update(request, 3L);
        } catch (Exception e) {}

        Assert.assertNotNull(myEntity);
        Assert.assertEquals(myEntity.getStatusCode(), HttpStatus.OK);
    }

    @Test
    public void updateBadRequestTest(){
        PricePerCabinPerRouteRequest request = new PricePerCabinPerRouteRequest();
        ResponseEntity myEntity = null;
        try {
            when(httpRequest.getParameter("routeId")).thenReturn(null);
            doNothing().when(service).update(request, null);
            myEntity = controller.update(request, httpRequest);
            verify(httpRequest, times(1)).getParameter("routeId");
        } catch (Exception e) {}

        Assert.assertNotNull(myEntity);
        Assert.assertEquals(myEntity.getStatusCode(), HttpStatus.BAD_REQUEST);
    }

    @Test
    public void updateNotFoundExceptionTest(){
        PricePerCabinPerRouteRequest request = new PricePerCabinPerRouteRequest();
        ResponseEntity myEntity = null;
        try {
            when(httpRequest.getParameter("routeId")).thenReturn("3");
            doThrow(new NotFoundException("error")).when(service).update(request, 3L);
            myEntity = controller.update(request, httpRequest);
            verify(httpRequest, times(2)).getParameter("routeId");
            verify(service).update(request, 3L);
        } catch (Exception e) {}

        Assert.assertNotNull(myEntity);
        Assert.assertEquals(myEntity.getStatusCode(), HttpStatus.NOT_FOUND);
    }

    @Test
    public void updateExceptionTest(){
        PricePerCabinPerRouteRequest request = new PricePerCabinPerRouteRequest();
        ResponseEntity myEntity = null;
        try {
            when(httpRequest.getParameter("routeId")).thenReturn("3");
            doThrow(new Exception("error")).when(service).update(request, 3L);
            myEntity = controller.update(request, httpRequest);
            verify(httpRequest, times(2)).getParameter("routeId");
            verify(service).update(request, 3L);
        } catch (Exception e) {}

        Assert.assertNotNull(myEntity);
        Assert.assertEquals(myEntity.getStatusCode(), HttpStatus.INTERNAL_SERVER_ERROR);
    }


}
