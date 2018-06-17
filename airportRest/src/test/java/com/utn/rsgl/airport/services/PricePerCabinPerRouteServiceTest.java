package com.utn.rsgl.airport.services;

import com.utn.rsgl.airport.models.*;
import com.utn.rsgl.airport.repositories.AirportRepository;
import com.utn.rsgl.airport.repositories.CabinRepository;
import com.utn.rsgl.airport.repositories.PricePerCabinPerRouteRepository;
import com.utn.rsgl.airport.repositories.RouteRepository;
import com.utn.rsgl.airport.requests.PricePerCabinPerRouteRequest;
import com.utn.rsgl.airport.service.PricePerCabinPerRouteService;
import com.utn.rsgl.core.shared.dto.*;
import com.utn.rsgl.core.shared.utils.DateUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.mockito.Mockito.verify;
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
    private PricePerCabinPerRouteDTO pricePerRouteDto;
    private PricePerCabinPerRoute pricePerRouteReal;

    @Before
    public void init() {
        this.service = new PricePerCabinPerRouteService();
        MockitoAnnotations.initMocks(this);
        service.setPriceRepository(repository);
        service.setAirportRepository(airportRepository);
        service.setCabinRepository(cabinRepository);
        service.setRouteRepository(routeRepository);
        vigencyFrom = DateUtils.StringToDate("2018-03-27");
        vigencyTo = DateUtils.StringToDate("2018-10-12");
        initPricePerRouteRequest();
        initPricePerRouteDto();
        initPricePerRouteReal();
    }

    private void initPricePerRouteRequest() {
        pricePerRouteRequest = new PricePerCabinPerRouteRequest();
        pricePerRouteRequest.setActive(true);
        pricePerRouteRequest.setArrivalAirportIataCode("LAN");
        pricePerRouteRequest.setDepartureAirportIataCode("NIC");
        pricePerRouteRequest.setId(1);
        pricePerRouteRequest.setVigencyFrom(DateUtils.DateToString(vigencyFrom));
        pricePerRouteRequest.setVigencyTo(DateUtils.DateToString(vigencyTo));
        pricePerRouteRequest.setPrice(8.30D);
        pricePerRouteRequest.setCabinName("Economica");
    }

    private void initPricePerRouteDto() {
        pricePerRouteDto = new PricePerCabinPerRouteDTO();
        CabinDTO cabin = new CabinDTO("Economica");
        RouteDTO route = new RouteDTO();
        AirportDTO arrivalAirport = new AirportDTO();
        AirportDTO departureAirport = new AirportDTO();
        CityDTO city = new CityDTO();
        CityDTO departureCity = new CityDTO();
        StateDTO state = new StateDTO();
        StateDTO departureState = new StateDTO();
        CountryDTO country = new CountryDTO();
        CountryDTO departureCountry = new CountryDTO();
        state.setCountry(country);
        pricePerRouteDto.setActive(true);
        arrivalAirport.setIataCode("LAN");
        arrivalAirport.setName("AeroLineas Argentinas");
        city.setIataCode("CAP");
        city.setName("Capital");
        state.setName("Buenos Aires");
        country.setIsoCode3("ARG");
        country.setName("Argentina");
        city.setState(state);
        arrivalAirport.setCity(city);
        route.setArrivalAirport(arrivalAirport);
        departureAirport.setName("Nicaragua Aerolines");
        departureAirport.setIataCode("NIC");
        departureCity.setName("Bianca Marina");
        departureCity.setIataCode("BNM");
        departureState.setName("Bello paraiso");
        departureCountry.setName("Nicaragua");
        departureCountry.setIsoCode3("NIA");
        departureState.setCountry(departureCountry);
        departureCity.setState(departureState);
        departureAirport.setCity(departureCity);
        route.setDepartureAirport(departureAirport);
        route.setArrivalAirport(arrivalAirport);
        pricePerRouteDto.setRoute(route);
        pricePerRouteDto.setVigencyFrom(DateUtils.DateToString(vigencyFrom));
        pricePerRouteDto.setVigencyTo(DateUtils.DateToString(vigencyTo));
        pricePerRouteDto.setPrice(8.30D);
        pricePerRouteDto.setCabin(cabin);
        pricePerRouteDto.setActive(true);
    }

    private void initPricePerRouteReal() {
        pricePerRouteReal = new PricePerCabinPerRoute();
        Cabin cabin = new Cabin("Economica");
        Route route = new Route();
        Airport arrivalAirport = new Airport();
        Airport departureAirport = new Airport();
        City city = new City();
        City departureCity = new City();
        State state = new State();
        State departureState = new State();
        Country country = new Country();
        Country departureCountry = new Country();
        pricePerRouteReal.setActive(true);
        arrivalAirport.setIataCode("LAN");
        arrivalAirport.setName("AeroLineas Argentinas");
        arrivalAirport.setId(1);
        city.setIataCode("CAP");
        city.setName("Capital");
        city.setId(1);
        state.setName("Buenos Aires");
        state.setId(1);
        country.setIsoCode3("ARG");
        country.setName("Argentina");
        country.setId(1);
        state.setCountry(country);
        city.setState(state);
        arrivalAirport.setCity(city);
        departureAirport.setName("Nicaragua Aerolines");
        departureAirport.setIataCode("NIC");
        departureAirport.setId(2);
        departureCity.setName("Bianca Marina");
        departureCity.setIataCode("BNM");
        departureCity.setId(2);
        departureState.setName("Bello paraiso");
        departureState.setId(2);
        departureCountry.setName("Nicaragua");
        departureCountry.setIsoCode3("NIA");
        departureCountry.setId(2);
        departureState.setCountry(departureCountry);
        departureCity.setState(departureState);
        departureAirport.setCity(departureCity);
        route.setDepartureAirport(departureAirport);
        route.setArrivalAirport(arrivalAirport);
        route.setId(1);
        pricePerRouteReal.setRoute(route);
        pricePerRouteReal.setVigencyFrom(vigencyFrom);
        pricePerRouteReal.setVigencyTo(vigencyTo);
        pricePerRouteReal.setPrice(8.30D);
        pricePerRouteReal.setCabin(cabin);
        pricePerRouteReal.setActive(true);
    }

    @Test
    public void saveTest() {
        Route route = new Route();
        Airport arrivalAirport = pricePerRouteReal.getRoute().getArrivalAirport();
        Airport departureAirport = pricePerRouteReal.getRoute().getDepartureAirport();
        Cabin cabin = pricePerRouteReal.getCabin();
        route.setArrivalAirport(arrivalAirport);
        route.setDepartureAirport(departureAirport);
        route.setId(1);

        List<PricePerCabinPerRoute> pricesRealList = new ArrayList<>();
        pricesRealList.add(pricePerRouteReal);

        when(airportRepository.findAirportByIataCode(pricePerRouteRequest.getArrivalAirportIataCode()))
                .thenReturn(arrivalAirport);
        when(airportRepository.findAirportByIataCode(pricePerRouteRequest.getDepartureAirportIataCode()))
                .thenReturn(departureAirport);
        when(routeRepository.findRouteByArrivalAirportAndDepartureAirport(arrivalAirport, departureAirport))
                .thenReturn(route);
        when(cabinRepository.findCabinByName(pricePerRouteRequest.getCabinName()))
                .thenReturn(cabin);

        try{
            service.save(pricePerRouteRequest);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

        verify(airportRepository).findAirportByIataCode(pricePerRouteRequest.getArrivalAirportIataCode());
        verify(cabinRepository).findCabinByName("Economica");
        verify(airportRepository).findAirportByIataCode(pricePerRouteRequest.getDepartureAirportIataCode());
        verify(repository).save(pricePerRouteReal);
    }

    @Test
    public void saveIllegalArgumentExceptionTest() {
        boolean catched = false;
        PricePerCabinPerRouteRequest request = null;
        try {
            service.save(request);
        } catch (Exception e) {
            Assert.assertTrue(e instanceof IllegalArgumentException);
            catched = true;
        }
        Assert.assertTrue(catched);

        pricePerRouteRequest.setVigencyTo("");
        assertIllegalArgumentExceptionOnSave();
        pricePerRouteRequest.setVigencyTo(null);
        assertIllegalArgumentExceptionOnSave();
        pricePerRouteRequest.setVigencyFrom("");
        assertIllegalArgumentExceptionOnSave();
        pricePerRouteRequest.setVigencyFrom(null);
        assertIllegalArgumentExceptionOnSave();
        pricePerRouteRequest.setPrice(null);
        assertIllegalArgumentExceptionOnSave();
        pricePerRouteRequest.setDepartureAirportIataCode("");
        assertIllegalArgumentExceptionOnSave();
        pricePerRouteRequest.setDepartureAirportIataCode(null);
        assertIllegalArgumentExceptionOnSave();
        pricePerRouteRequest.setArrivalAirportIataCode("");
        assertIllegalArgumentExceptionOnSave();
        pricePerRouteRequest.setArrivalAirportIataCode(null);
        assertIllegalArgumentExceptionOnSave();
        pricePerRouteRequest.setCabinName("");
        assertIllegalArgumentExceptionOnSave();
        pricePerRouteRequest.setCabinName(null);
        assertIllegalArgumentExceptionOnSave();
    }

    private void assertIllegalArgumentExceptionOnSave() {
        boolean catched = false;
        try {
            service.save(pricePerRouteRequest);
        } catch (Exception e) {
            Assert.assertTrue(e instanceof IllegalArgumentException);
            catched = true;
        }
        Assert.assertTrue(catched);
    }

    @Test
    public void listPricesTest() {

        List<PricePerCabinPerRouteDTO> pricesDtosList = new ArrayList<>();
        pricesDtosList.add(pricePerRouteDto);
        List<PricePerCabinPerRoute> pricesRealList = new ArrayList<>();
        pricesRealList.add(pricePerRouteReal);
        List<PricePerCabinPerRouteDTO> obtainedPrices;

        when(repository.findAll()).thenReturn(pricesRealList);
        obtainedPrices = service.listAll();
        verify(repository).findAll();

        Assert.assertNotNull(obtainedPrices);
        Assert.assertEquals(pricesDtosList.size(), obtainedPrices.size());
        Assert.assertEquals(pricesDtosList, obtainedPrices);

        for (int i = 0; i < obtainedPrices.size(); i++) {
            Assert.assertEquals(pricesDtosList.get(i).getCabin(), obtainedPrices.get(i).getCabin());
            Assert.assertEquals(pricesDtosList.get(i).getPrice(), obtainedPrices.get(i).getPrice());
            Assert.assertEquals(pricesDtosList.get(i).getRoute(), obtainedPrices.get(i).getRoute());
            Assert.assertEquals(pricesDtosList.get(i).getVigencyFrom(), obtainedPrices.get(i).getVigencyFrom());
            Assert.assertEquals(pricesDtosList.get(i).getVigencyTo(), obtainedPrices.get(i).getVigencyTo());
            Assert.assertEquals(pricesDtosList.get(i).isActive(), obtainedPrices.get(i).isActive());
        }

    }

    @Test
    public void getByRouteTest(){

    }

    @Test
    public void deleteTest(){

    }


}