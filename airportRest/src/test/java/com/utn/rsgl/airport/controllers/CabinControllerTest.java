package com.utn.rsgl.airport.controllers;

import com.utn.rsgl.airport.exceptions.DataAlreadyExistsException;
import com.utn.rsgl.airport.factories.DtoFactory;
import com.utn.rsgl.airport.models.Cabin;
import com.utn.rsgl.airport.requests.CabinRequest;
import com.utn.rsgl.airport.service.CabinService;
import com.utn.rsgl.core.shared.dto.CabinDTO;
import javassist.NotFoundException;
import org.apache.maven.doxia.document.DocumentTOC;
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

import static org.mockito.Mockito.*;

public class CabinControllerTest {
    @Mock
    private CabinService cabinService;
    @Mock
    private HttpServletRequest request;

    private CabinController cabinController;

    @Before
    public void beforeTests() {
        MockitoAnnotations.initMocks(this);
        this.cabinController = new CabinController();
        cabinController.setCabinService(this.cabinService);
    }

    @Test
    public void saveTest() {
        CabinRequest cabin = new CabinRequest("turist");
        ResponseEntity myEntity = this.cabinController.addCabin(cabin);
        try {
            verify(cabinService).save(cabin);
        } catch (Exception e) {}

        Assert.assertEquals(HttpStatus.CREATED, myEntity.getStatusCode());
    }

    @Test
    public void saveIllegalArgumentExceptionTest() {
        CabinRequest cabin = new CabinRequest("turist");
        ResponseEntity myEntity = null;
        try {
            doThrow(new IllegalArgumentException("error")).when(cabinService).save(cabin);
            myEntity = this.cabinController.addCabin(cabin);
            verify(cabinService).save(cabin);
        } catch (Exception e) {
            Assert.assertTrue(e instanceof IllegalArgumentException);
        }
        Assert.assertEquals(HttpStatus.BAD_REQUEST, myEntity.getStatusCode());
    }

    @Test
    public void saveDataAlreadyExistsExceptionTest() {
        CabinRequest cabin = new CabinRequest("turist");
        ResponseEntity myEntity = null;
        try {
            doThrow(new DataAlreadyExistsException("error")).when(cabinService).save(cabin);
            myEntity = this.cabinController.addCabin(cabin);
            verify(cabinService).save(cabin);
        } catch (Exception e) {
            Assert.assertTrue(e instanceof DataAlreadyExistsException);
        }
        Assert.assertEquals(HttpStatus.IM_USED, myEntity.getStatusCode());
    }

    @Test
    public void saveExceptionTest() {
        CabinRequest cabin = new CabinRequest("turist");
        ResponseEntity myEntity = null;
        try {
            doThrow(new Exception("error")).when(cabinService).save(cabin);
            myEntity = this.cabinController.addCabin(cabin);
            verify(cabinService).save(cabin);
        } catch (Exception e) {
            Assert.assertTrue(e instanceof Exception);
        }
        Assert.assertNotNull(myEntity);
        Assert.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, myEntity.getStatusCode());
    }

    @Test
    public void getIllegalArgumentExceptionTest() {
        ResponseEntity entity = null;
        try {
            doThrow(new IllegalArgumentException("error")).when(cabinService).getCabin("jorge");
            when(request.getParameter("name")).thenReturn("jorge");
            entity = cabinController.getCabin(request);
            verify(request, times(2)).getParameter("name");
            verify(cabinService).getCabin("jorge");
        } catch (Exception e) {
            Assert.assertTrue(e instanceof IllegalArgumentException);
        }
        Assert.assertEquals(entity.getStatusCode(), HttpStatus.BAD_REQUEST);
    }

    @Test
    public void getNotFoundExceptionTest() {
        ResponseEntity entity = null;
        try {
            doThrow(new NotFoundException("error")).when(cabinService).getCabin("jorge");
            when(request.getParameter("name")).thenReturn("jorge");
            entity = cabinController.getCabin(request);
            verify(request, times(2)).getParameter("name");
            verify(cabinService).getCabin("jorge");
        } catch (Exception e) {
            Assert.assertTrue(e instanceof NotFoundException);
        }
        Assert.assertEquals(entity.getStatusCode(), HttpStatus.NOT_FOUND);
    }

    @Test
    public void getByOneExceptionTest() {
        ResponseEntity entity = null;
        try {
            doThrow(new Exception("error")).when(cabinService.getCabin("jorge"));
            when(request.getParameter("name")).thenReturn("jorge");
            entity = cabinController.getCabin(request);
            verify(request, times(2)).getParameter("name");
            verify(cabinService).getCabin("jorge");
        } catch (Exception e) {
            Assert.assertTrue(e instanceof Exception);
        }
        Assert.assertNotNull(entity);
        Assert.assertEquals(entity.getStatusCode(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @Test
    public void getAllCabinTest() {
        List<Cabin> cabins = new ArrayList<>();
        Cabin cabinA = new Cabin("standard");
        Cabin cabinB = new Cabin("premium");
        cabins.add(cabinA);
        cabins.add(cabinB);
        List<CabinDTO> cabinDtos = new ArrayList<>();
        cabinDtos.add(DtoFactory.getInstance().getDTOByModel(cabinA, CabinDTO.class));
        cabinDtos.add(DtoFactory.getInstance().getDTOByModel(cabinB, CabinDTO.class));
        List<CabinDTO> repository;

        when(request.getParameter("name")).thenReturn(null);
        when(cabinService.listAll()).thenReturn(cabinDtos);
        ResponseEntity entity = cabinController.getCabin(request);
        verify(request).getParameter("name");
        verify(cabinService).listAll();

        Assert.assertNotNull(entity);
        Assert.assertTrue(entity.hasBody());
        Assert.assertEquals(entity.getStatusCode(), HttpStatus.ACCEPTED);
        repository = (List<CabinDTO>) entity.getBody();
        Assert.assertNotNull(repository);
        Assert.assertEquals(cabinDtos.size(), repository.size());
        Assert.assertArrayEquals(cabinDtos.toArray(), repository.toArray());
    }

    @Test
    public void getOneCabinTest() {
        CabinDTO cabinDto = new CabinDTO("Economica");
        List<CabinDTO> obtainedCabin = null;
        ResponseEntity entity = null;

        try {
            when(request.getParameter("name")).thenReturn("Economica");
            when(cabinService.getCabin("Economica")).thenReturn(cabinDto);
            entity = cabinController.getCabin(request);
            verify(request, times(2)).getParameter("name");
            verify(cabinService).getCabin("Economica");
            obtainedCabin = (List<CabinDTO>) entity.getBody();
        } catch (Exception e) {
            e.printStackTrace();
        }

        Assert.assertNotNull(entity);
        Assert.assertTrue(entity.hasBody());
        Assert.assertEquals(entity.getStatusCode(), HttpStatus.ACCEPTED);
        Assert.assertNotNull(obtainedCabin);
        Assert.assertEquals(obtainedCabin.size(), 1);
        Assert.assertEquals(obtainedCabin.get(0).getName(), cabinDto.getName());
    }

}