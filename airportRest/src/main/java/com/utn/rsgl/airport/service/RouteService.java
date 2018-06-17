package com.utn.rsgl.airport.service;

import com.utn.rsgl.airport.exceptions.DataAlreadyExistsException;
import com.utn.rsgl.airport.factories.DtoFactory;
import com.utn.rsgl.airport.models.Airport;
import com.utn.rsgl.airport.models.Route;
import com.utn.rsgl.airport.repositories.AirportRepository;
import com.utn.rsgl.airport.repositories.RouteRepository;
import com.utn.rsgl.airport.requests.RouteRequest;
import com.utn.rsgl.core.shared.dto.RouteDTO;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class RouteService {

    @Autowired RouteRepository routeRepository;
    @Autowired AirportRepository airportRepository;

    /** if the route dont exists, it created a new route and save it in the database
     *
     * @param routeRequest with the iataCode of the arrival airport and the departure airport
     */
    public void newRoute(RouteRequest routeRequest) throws DataAlreadyExistsException, Exception {
        Route route = new Route();
        route.setDepartureAirport(airportRepository.findAirportByIataCode(routeRequest.getDepartureAirport()));
        route.setArrivalAirport(airportRepository.findAirportByIataCode(routeRequest.getArrivalAirport()));
        if(Objects.isNull(findRoute(routeRequest))){
            routeRepository.save(route);
        }else{
            throw new DataAlreadyExistsException("The route between the airports " + routeRequest.getArrivalAirport()
                                                + " - " + routeRequest.getDepartureAirport() + " already exists");
        }
    }

    /** look in the repository if the route exists, in that case it returns the Route, if not, it returns null
     *
     * @param routeRequest : contains both departure and arrival airports iata code.
     * @return the Route between them
     */
    public RouteDTO getRoute(RouteRequest routeRequest) throws NotFoundException, Exception {
        Route route = findRoute(routeRequest);
        if(Objects.isNull(route)){
            throw new NotFoundException("A route between the airports : " + routeRequest.getDepartureAirport() + " - "
                                        + routeRequest.getArrivalAirport() + " doesn't exists.");
        }
        return DtoFactory.getInstance().getDTOByModel(route, RouteDTO.class);
    }

    /** to find all the routes
     *
     * @return a list of routes
     */
    public List<RouteDTO> allRoutes(){
        List<RouteDTO> routesDTO = new ArrayList<>();
        List<Route> routes = routeRepository.findAll();
        for (Route theRoute : routes) {
            RouteDTO route = DtoFactory.getInstance().getDTOByModel(theRoute, RouteDTO.class);
            routesDTO.add(route);
        }
        return routesDTO;
    }

    public void updateRoute(RouteRequest routeRequest, long oldId) throws NotFoundException {
        Route route = findRoute(routeRequest);
        if(Objects.isNull(route)){
            throw new NotFoundException("there is no route between the airports " + routeRequest.getDepartureAirport()
                                        + " - " + routeRequest.getArrivalAirport());
        }
        routeRepository.update(oldId, route.getArrivalAirport(), route.getDepartureAirport());
    }

    public void deleteRoute(RouteRequest routeRequest) throws NotFoundException {
        Route route = findRoute(routeRequest);
        if(Objects.isNull(route)){
            throw new NotFoundException("there is no route between the airports " + routeRequest.getDepartureAirport()
                                        + " - " + routeRequest.getArrivalAirport());
        }
        routeRepository.delete(route);
    }

    private Route findRoute(RouteRequest routeRequest){
        Airport arrivalAirport = airportRepository.findAirportByIataCode(routeRequest.getArrivalAirport());
        Airport departureAirport = airportRepository.findAirportByIataCode(routeRequest.getDepartureAirport());
        return routeRepository.findRouteByArrivalAirportAndDepartureAirport(arrivalAirport, departureAirport);
    }
}
