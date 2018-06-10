package com.utn.rsgl.airport.service;

import com.utn.rsgl.airport.factories.DtoFactory;
import com.utn.rsgl.airport.models.Airport;
import com.utn.rsgl.airport.models.Cabin;
import com.utn.rsgl.airport.models.PricePerCabinPerRoute;
import com.utn.rsgl.airport.models.Route;
import com.utn.rsgl.airport.repositories.*;
import com.utn.rsgl.airport.requests.PricePerCabinPerRouteRequest;
import com.utn.rsgl.core.shared.dto.PricePerCabinPerRouteDTO;
import com.utn.rsgl.core.shared.utils.DateUtils;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
public class PricePerCabinPerRouteService {

    @Autowired
    private PricePerCabinPerRouteRepository priceRepository;
    @Autowired
    private AirportRepository airportRepository;
    @Autowired
    private RouteRepository routeRepository;
    @Autowired
    private CabinRepository cabinRepository;

    public void save(PricePerCabinPerRouteRequest request){
        Airport arrivalAirport = airportRepository.findAirportByIataCode(request.getArrivalAirportIataCode());
        Airport departureAirport = airportRepository.findAirportByIataCode(request.getDepartureAirportIataCode());

        Route route = routeRepository.findRouteByArrivalAirportAndDepartureAirport(arrivalAirport, departureAirport);
        Date vigencyFrom = DateUtils.StringToDate(request.getVigencyFrom());
        Date vigencyTo = DateUtils.StringToDate(request.getVigencyTo());
        Cabin cabin = cabinRepository.findCabinByName(request.getCabinName());

        PricePerCabinPerRoute price = new PricePerCabinPerRoute(route, vigencyFrom, vigencyTo, cabin,
                                                                request.getPrice(), request.isActive());
        priceRepository.save(price);
    }

    public List<PricePerCabinPerRouteDTO> listAll(){
        List<PricePerCabinPerRoute> prices = priceRepository.findAll();
        List<PricePerCabinPerRouteDTO> pricesDTO = new ArrayList<>();

        for(PricePerCabinPerRoute price : prices){
            pricesDTO.add(DtoFactory.getInstance().getDTOByModel(price, PricePerCabinPerRouteDTO.class));
        }

        return pricesDTO;
    }

    public void update(PricePerCabinPerRouteRequest request, Long id) throws NotFoundException {
        PricePerCabinPerRoute originalRoute = priceRepository.findById(id).get();
        Airport arrival = airportRepository.findAirportByIataCode(request.getArrivalAirportIataCode());
        Airport departure = airportRepository.findAirportByIataCode(request.getDepartureAirportIataCode());
        Date vigencyFrom = DateUtils.StringToDate(request.getVigencyFrom());
        Date vigencyTo = DateUtils.StringToDate(request.getVigencyTo());
        if(Objects.isNull(originalRoute)){
            throw new NotFoundException("cant find the route with the id :" + id);
        }
        originalRoute.setActive(request.isActive());
        originalRoute.setCabin(cabinRepository.findCabinByName(request.getCabinName()));
        originalRoute.setPrice(request.getPrice());
        originalRoute.setRoute(routeRepository.findRouteByArrivalAirportAndDepartureAirport(arrival, departure));
        originalRoute.setVigencyFrom(vigencyFrom);
        originalRoute.setVigencyTo(vigencyTo);

        priceRepository.update(originalRoute);
    }

    public List<PricePerCabinPerRouteDTO> getByRoute(PricePerCabinPerRouteRequest request){
        Airport arrivalAirport = airportRepository.findAirportByIataCode(request.getArrivalAirportIataCode());
        Airport departureAirport = airportRepository.findAirportByIataCode(request.getDepartureAirportIataCode());
        Route route = routeRepository.findRouteByArrivalAirportAndDepartureAirport(arrivalAirport, departureAirport);
        List<PricePerCabinPerRouteDTO> pricesDto = new ArrayList<>();
        List<PricePerCabinPerRoute> prices = priceRepository.findPricePerCabinPerRouteByRoute(route);

        for(PricePerCabinPerRoute price : prices){
            pricesDto.add(DtoFactory.getInstance().getDTOByModel(price, PricePerCabinPerRouteDTO.class));
        }
        return pricesDto;
    }

    public void delete(PricePerCabinPerRouteRequest request) {
        Airport arrivalAirport = airportRepository.findAirportByIataCode(request.getArrivalAirportIataCode());
        Airport departureAirport = airportRepository.findAirportByIataCode(request.getDepartureAirportIataCode());
        Route route = routeRepository.findRouteByArrivalAirportAndDepartureAirport(arrivalAirport, departureAirport);
        Date vigencyFrom = DateUtils.StringToDate(request.getVigencyFrom());
        Date vigencyTo = DateUtils.StringToDate(request.getVigencyTo());
        Cabin cabin = cabinRepository.findCabinByName(request.getCabinName());
        PricePerCabinPerRoute price = new PricePerCabinPerRoute(route, vigencyFrom, vigencyTo, cabin, request.getPrice(), request.isActive());

        priceRepository.delete(price);

    }
}
