package com.utn.rsgl.airport.controllers;

import com.utn.rsgl.airport.exceptions.DataAlreadyExistsException;
import com.utn.rsgl.airport.requests.CabinRequest;
import com.utn.rsgl.airport.service.CabinService;
import com.utn.rsgl.core.shared.dto.CabinDTO;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;
import javax.xml.crypto.Data;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class CabinControllerTest {
    @Mock
    private CabinService cabinService;
    @Mock
    private HttpServletRequest request;
    private CabinController cabinController;

    @Before
    public void beforeTests(){
        MockitoAnnotations.initMocks(this);
        this.cabinController= new CabinController();
        cabinController.setCabinService(this.cabinService);
    }

    @Test
    public void addCabinTestHappyWay(){
        CabinRequest cabin = new CabinRequest("turist");
        ResponseEntity myEntity = this.cabinController.addCabin(cabin);
        try{
            verify(cabinService).saveCabin(cabin);
        } catch (Exception e) {}
        Assert.assertEquals(HttpStatus.CREATED, myEntity.getStatusCode());
    }

    @Test
    public void addCabinTestSadWay(){
        CabinRequest cabin = new CabinRequest("turist");
        ResponseEntity myEntity;
        try{
            doThrow(new DataAlreadyExistsException("error")).when(cabinService).saveCabin(cabin);
            myEntity = this.cabinController.addCabin(cabin);
        } catch (Exception e) {}
        Assert.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, myEntity.getStatusCode());
    }

    @Test
    public void getCabinHappyWayWithParameter(){
        when(request.getParameter("name")).thenReturn("turist");
        when(cabinService.getCabin("turist")).thenReturn(new CabinDTO("turist"));

        ResponseEntity myEntity = cabinController.getCabin(request);

        verify(request).getParameter("name");
        verify(cabinService).getCabin("tourist");

        List<CabinDTO> myCabin = (List<CabinDTO>) myEntity.getBody();

        Assert.assertNotNull(myEntity);
        Assert.assertTrue(myEntity.hasBody());
        Assert.assertEquals(myEntity.getStatusCode(), HttpStatus.ACCEPTED);
        //Assert.assertTrue(myEntity.getBody() instanceof  List<CabinDTO>);
        Assert.assertEquals(1, myCabin.size());
        Assert.assertEquals("turist", myCabin.get(0).getName());
    }

    @Test
    public void getCabinHappyWayWithParameter2(){
        List<CabinDTO> cabins = new ArrayList<>();
        cabins.add(new CabinDTO("tourist"));
        cabins.add(new CabinDTO("first class"));

        when(request.getParameter("name")).thenReturn(null);
        when(cabinService.getAll()).thenReturn(cabins);

        ResponseEntity myEntity = cabinController.getCabin(request);

        verify(request).getParameter("name");
        verify(cabinService).getAll();

        List<CabinDTO> mycabin = (List<CabinDTO>) myEntity.getBody();

        Assert.assertNotNull(myEntity);
        Assert.assertTrue(myEntity.hasBody());
        Assert.assertEquals(myEntity.getStatusCode(), HttpStatus.ACCEPTED);
        //Assert.assertTrue(myEntity.getBody() instanceof  List<CabinDTO>);
        Assert.assertEquals(2, mycabin.size());
        Assert.assertEquals("turist", mycabin.get(0).getName());
    }

    @Test
    public void getCabinSAdWayTest(){
        when(request.getParameter("name")).thenReturn(null);
        when(cabinService.getAll()).thenThrow(new Exception());
        ResponseEntity myEntity = this.cabinController.getCabin(request);
        Assert.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, myEntity.getStatusCode());
    }
}

