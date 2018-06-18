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
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
public class PricePerCabinPerRouteService {

    @Autowired
    @Setter
    private PricePerCabinPerRouteRepository priceRepository;
    @Autowired
    @Setter
    private AirportRepository airportRepository;
    @Autowired
    @Setter
    private RouteRepository routeRepository;
    @Autowired
    @Setter
    private CabinRepository cabinRepository;

    public void save(PricePerCabinPerRouteRequest request){
        if(request == null || request.getCabinName() == null || request.getCabinName().equals("")
                || request.getArrivalAirportIataCode() == null || request.getArrivalAirportIataCode().equals("")
                || request.getDepartureAirportIataCode() == null || request.getDepartureAirportIataCode().equals("")
                || request.getPrice() == null || request.getVigencyFrom() == null || request.getVigencyFrom().equals("")
                || request.getVigencyTo() == null || request.getVigencyTo().equals("")) {
            throw new IllegalArgumentException("some values of the request are null or empty. Please verify");
        }
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
            PricePerCabinPerRouteDTO priceDto;
            priceDto = DtoFactory.getInstance().getDTOByModel(price, PricePerCabinPerRouteDTO.class);
            priceDto.setVigencyFrom(DateUtils.DateToString(price.getVigencyFrom()));
            priceDto.setVigencyTo(DateUtils.DateToString(price.getVigencyTo()));
            pricesDTO.add(priceDto);
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

        priceRepository.update(request.isActive(), request.getPrice(),
                routeRepository.findRouteByArrivalAirportAndDepartureAirport(arrival, departure),
                cabinRepository.findCabinByName(request.getCabinName()), vigencyFrom, vigencyTo, originalRoute.getId());
    }

    public List<PricePerCabinPerRouteDTO> getByRoute(String arrivalAirportIatacode, String departureAirportIataCode){
        Airport arrivalAirport = airportRepository.findAirportByIataCode(arrivalAirportIatacode);
        Airport departureAirport = airportRepository.findAirportByIataCode(departureAirportIataCode);
        Route route = routeRepository.findRouteByArrivalAirportAndDepartureAirport(arrivalAirport, departureAirport);

        List<PricePerCabinPerRouteDTO> pricesDto = new ArrayList<>();
        List<PricePerCabinPerRoute> prices = priceRepository.findPricePerCabinPerRouteByRoute(route);

        return convertPriceRoutesDates(prices, pricesDto);
    }

    private List<PricePerCabinPerRouteDTO> convertPriceRoutesDates(List<PricePerCabinPerRoute> prices,
                                                                   List<PricePerCabinPerRouteDTO> pricesDto){
        for(PricePerCabinPerRoute price : prices){
            PricePerCabinPerRouteDTO priceDto = DtoFactory.getInstance().getDTOByModel(price, PricePerCabinPerRouteDTO.class);
            priceDto.setVigencyFrom(DateUtils.DateToString(price.getVigencyFrom()));
            priceDto.setVigencyTo(DateUtils.DateToString(price.getVigencyTo()));
            pricesDto.add(priceDto);
        }
        return pricesDto;
    }

    public List<PricePerCabinPerRouteDTO> getByRouteAndDates(String arrivalIataCode, String departureIataCode,
                                                             String from, String to) {
        Airport arrivalAirport = airportRepository.findAirportByIataCode(arrivalIataCode);
        Airport departureAirport = airportRepository.findAirportByIataCode(departureIataCode);
        Date fromDate = DateUtils.StringToDate(from);
        Date toDate = DateUtils.StringToDate(to);
        Route route = routeRepository.findRouteByArrivalAirportAndDepartureAirport(arrivalAirport, departureAirport);

        List<PricePerCabinPerRouteDTO> pricesDto = new ArrayList<>();
        List<PricePerCabinPerRoute> prices = priceRepository.findPricePerCabinPerRouteByRouteAndVigencyFromAndVigencyTo(
                                                                route, fromDate, toDate);

        return convertPriceRoutesDates(prices, pricesDto);
    }


    public List<PricePerCabinPerRouteDTO> getByDates(String from, String to) {
        Date fromDate = DateUtils.StringToDate(from);
        Date toDate = DateUtils.StringToDate(to);

        List<PricePerCabinPerRouteDTO> pricesDto = new ArrayList<>();
        List<PricePerCabinPerRoute> prices = priceRepository.findPricePerCabinPerRouteByVigencyFromAndVigencyTo(
                                                                fromDate, toDate);
        return convertPriceRoutesDates(prices, pricesDto);
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
