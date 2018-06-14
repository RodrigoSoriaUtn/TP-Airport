package com.utn.rsgl.airport.services;

import com.utn.rsgl.airport.exceptions.DataAlreadyExistsException;
import com.utn.rsgl.airport.models.Cabin;
import com.utn.rsgl.airport.repositories.CabinRepository;
import com.utn.rsgl.airport.requests.CabinRequest;
import com.utn.rsgl.airport.service.CabinService;
import com.utn.rsgl.core.shared.dto.CabinDTO;
import javassist.NotFoundException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

public class CabinServiceTest {


    @Mock
    private CabinRepository cabinRepository;

    private CabinService cabinService;

    private String cabinName = "Economica";

    @Before
    public void init(){
        this.cabinService = new CabinService();
        MockitoAnnotations.initMocks(this);
        cabinService.setCabinRepository(cabinRepository);
    }

    @Test
    public void saveDataAlreadyExistsExceptionTest(){
        boolean catched = false;
        when(cabinRepository.findCabinByName(cabinName)).thenReturn(new Cabin(cabinName));
        try {
            cabinService.save(new CabinRequest(cabinName));
            verify(cabinRepository).findCabinByName(cabinName);
        } catch (Exception e) {
            Assert.assertTrue(e instanceof DataAlreadyExistsException);
            catched = true;
        }
        Assert.assertTrue(catched);
    }

    @Test
    public void saveEmptyOrNullCabinTest(){
        boolean catched = false;
        when(cabinRepository.findCabinByName(cabinName)).thenReturn(null);
        try{
            cabinService.save(null);
        } catch (Exception e) {
            Assert.assertTrue(e instanceof IllegalArgumentException);
            catched = true;
        }
        Assert.assertTrue(catched);

        try{
            cabinService.save(new CabinRequest(null));
        } catch (Exception e) {
            Assert.assertTrue(e instanceof IllegalArgumentException);
            catched = true;
        }
        Assert.assertTrue(catched);

        try{
            cabinService.save(new CabinRequest(""));
        } catch (Exception e) {
            Assert.assertTrue(e instanceof IllegalArgumentException);
            catched = true;
        }
        Assert.assertTrue(catched);
    }

    @Test
    public void saveCabinTest(){
        List<Cabin> cabins = new ArrayList<>();

        when(cabinRepository.findCabinByName(cabinName)).thenReturn(null);
        try {
            Cabin cabin = new Cabin(cabinName);
            cabinService.save(new CabinRequest(cabinName));
            verify(cabinRepository).save(cabin);
            cabins.add(cabin);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Assert.assertEquals(cabins.get(0).getName(),cabinName);
    }

    @Test
    public void listCabinsTest(){

        List<Cabin> cabins = new ArrayList<>();
        List<CabinDTO> cabinDtos = new ArrayList<>();
        List<CabinDTO> repository;

        cabins.add(new Cabin("Economica"));
        cabins.add(new Cabin("King"));
        cabinDtos.add(new CabinDTO("Economica"));
        cabinDtos.add(new CabinDTO("King"));

        when(cabinRepository.findAll()).thenReturn(cabins);
        repository = cabinService.listAll();
        verify(cabinRepository).findAll();

        Assert.assertEquals(repository.size(), cabins.size());
        Assert.assertEquals(repository.size(), cabinDtos.size());
        for(int i = 0; i < repository.size(); i++){
            Assert.assertEquals(repository.get(i).getName(), cabinDtos.get(i).getName());
        }
    }

    @Test
    public void removeNotFoundExceptionTest(){
        boolean catched = false;
        when(cabinRepository.findCabinByName(cabinName)).thenReturn(null);
        try {
            cabinService.delete(new CabinRequest(cabinName));
            verify(cabinRepository).findCabinByName(cabinName);
        } catch (Exception e) {
            Assert.assertTrue(e instanceof NotFoundException);
            catched = true;
        }
        Assert.assertTrue(catched);
    }


    @Test
    public void removeIllegalArgumentExceptionTest(){
        boolean catched = false;
        when(cabinRepository.findCabinByName(cabinName)).thenReturn(null);
        try {
            cabinService.delete(new CabinRequest(cabinName));
            verify(cabinRepository).findCabinByName(cabinName);
        } catch (Exception e) {
            Assert.assertTrue(e instanceof IllegalArgumentException);
            catched = true;
        }
        Assert.assertTrue(catched);
    }

    @Test
    public void removeTest(){
        boolean deleted = false;
        when(cabinRepository.findCabinByName(cabinName)).thenReturn(new Cabin(cabinName));
        try {
            cabinService.delete(new CabinRequest(cabinName));
            verify(cabinRepository).findCabinByName(cabinName);
            verify(cabinRepository).delete(new Cabin(cabinName));
            deleted = true;
        } catch (NotFoundException e) {
            e.printStackTrace();
        }
        Assert.assertTrue(deleted);
    }

    @Test
    public void updateNotFoundExceptionTest(){
        String previousCabinName = "King";
        when(cabinRepository.findCabinByName(previousCabinName)).thenReturn(null);
        when(cabinRepository.findCabinByName(cabinName)).thenReturn(null);
        boolean catched = false;
        try {
            cabinService.update(new CabinRequest(cabinName), previousCabinName);
            verify(cabinRepository).findCabinByName(previousCabinName);
        } catch (Exception e) {
            Assert.assertTrue(e instanceof NotFoundException);
            catched = true;
        }
        Assert.assertTrue(catched);
    }

    @Test
    public void updateDataAlreadyExistsExceptionTest(){
        String previousCabinName = "Queen";
        Cabin previousCabin = new Cabin(previousCabinName);
        Cabin cabin = new Cabin(cabinName);
        previousCabin.setId(5);
        cabin.setId(3);
        when(cabinRepository.findCabinByName(previousCabinName)).thenReturn(previousCabin);
        when(cabinRepository.findCabinByName(cabinName)).thenReturn(cabin);
        boolean catched = false;
        try {
            cabinService.update(new CabinRequest(cabinName), previousCabinName);
            verify(cabinRepository).findCabinByName(previousCabinName);
            verify(cabinRepository).findCabinByName(cabinName);
        } catch (Exception e) {
            Assert.assertTrue(e instanceof DataAlreadyExistsException);
            catched = true;
        }
        Assert.assertNotEquals(cabin.getId(), previousCabin.getId());
        Assert.assertNotEquals(cabin.getName(), previousCabin.getName());
        Assert.assertTrue(catched);
    }

    @Test
    public void updateTest(){
        String previousCabinName = "Queen";
        Cabin previousCabin = new Cabin(previousCabinName);
        previousCabin.setId(5);
        when(cabinRepository.findCabinByName(previousCabinName)).thenReturn(previousCabin);
        when(cabinRepository.findCabinByName(cabinName)).thenReturn(null);
        boolean itWorked = false;
        try {
            cabinService.update(new CabinRequest(cabinName), previousCabinName);
            verify(cabinRepository).findCabinByName(previousCabinName);
            verify(cabinRepository).findCabinByName(cabinName);
            verify(cabinRepository).update(previousCabinName, previousCabin.getId());
            itWorked = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        Assert.assertTrue(itWorked);
    }
}
