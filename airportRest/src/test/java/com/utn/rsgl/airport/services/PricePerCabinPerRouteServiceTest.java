package com.utn.rsgl.airport.services;

import com.utn.rsgl.airport.models.Airport;
import com.utn.rsgl.airport.models.Cabin;
import com.utn.rsgl.airport.models.Route;
import com.utn.rsgl.airport.repositories.AirportRepository;
import com.utn.rsgl.airport.repositories.CabinRepository;
import com.utn.rsgl.airport.repositories.PricePerCabinPerRouteRepository;
import com.utn.rsgl.airport.repositories.RouteRepository;
import com.utn.rsgl.airport.requests.PricePerCabinPerRouteRequest;
import com.utn.rsgl.airport.service.PricePerCabinPerRouteService;
import com.utn.rsgl.core.shared.utils.DateUtils;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Date;

import static org.mockito.Mockito.when;

public class PricePerCabinPerRouteServiceTest {

    @Mock
    private PricePerCabinPerRouteRepository repository;

    @Mock
    private AirportRepository airportRepository;

    @Mock
    private RouteRepository routeRepository;

    @Mock
    private CabinRepository cabinRepository;

    private PricePerCabinPerRouteService service;

    private Date vigencyFrom;
    private Date vigencyTo;
    private PricePerCabinPerRouteRequest pricePerRouteRequest;

    @Before
    public void init(){
        this.service = new PricePerCabinPerRouteService();
        MockitoAnnotations.initMocks(this);
        service.setPricePerCabinPerRouteRepository(repository);
        pricePerRouteRequest = new PricePerCabinPerRouteRequest();
        pricePerRouteRequest.setActive(true);
        pricePerRouteRequest.setArrivalAirportIataCode("LAN");
        pricePerRouteRequest.setDepartureAirportIataCode("NIC");
        pricePerRouteRequest.setId(1);
        pricePerRouteRequest.setVigencyFrom("27-3-2018");
        pricePerRouteRequest.setVigencyTo("12-10-2018");
        pricePerRouteRequest.setPrice(8.30);
        pricePerRouteRequest.setCabinName("Economica");
        vigencyFrom = DateUtils.StringToDate("27-3-2018");
        vigencyTo = DateUtils.StringToDate("12-10-2018");
    }

    @Test
    public void saveDataAlreadyExistsExceptionTest(){
        boolean catched = false;
        Airport arrivalAirport = new Airport("AeroLineas Argentinas", "LAN");
        Airport departureAirport = new Airport("Nicaragua Aerolines", "NIC");
        Route route = new Route();
        route.setArrivalAirport(arrivalAirport);
        route.setDepartureAirport(departureAirport);
        Cabin cabin = new Cabin("Economica");

        when(airportRepository.findAirportByIataCode(pricePerRouteRequest.getDepartureAirportIataCode()))
                .thenReturn(arrivalAirport);
        when(airportRepository.findAirportByIataCode(pricePerRouteRequest.getDepartureAirportIataCode()))
                .thenReturn(departureAirport);
        when(routeRepository.findRouteByArrivalAirportAndDepartureAirport(arrivalAirport, departureAirport)).thenReturn(route);
        when(cabinRepository.findCabinByName("Economica"));


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
