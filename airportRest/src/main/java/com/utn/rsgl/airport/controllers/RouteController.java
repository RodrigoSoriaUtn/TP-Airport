package com.utn.rsgl.airport.controllers;

import com.utn.rsgl.airport.exceptions.DataAlreadyExistsException;
import com.utn.rsgl.airport.requests.RouteRequest;
import com.utn.rsgl.airport.service.RouteService;
import com.utn.rsgl.core.shared.dto.RouteDTO;
import javassist.NotFoundException;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RestController
@Data
@RequestMapping(value = "/route", produces = MediaType.APPLICATION_JSON_VALUE)
public class RouteController {

    @Autowired
    private RouteService routeService;

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity addRoute(@RequestBody RouteRequest routeRequest){
        ResponseEntity myResponseEntity;
        try{
            routeService.newRoute(routeRequest);
            myResponseEntity = new ResponseEntity(HttpStatus.CREATED);
        } catch( DataAlreadyExistsException e) {
            myResponseEntity = new ResponseEntity(HttpStatus.IM_USED);
        } catch(Exception e){
            myResponseEntity = new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return  myResponseEntity;
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<RouteDTO>> getRoute(HttpServletRequest request){
        List<RouteDTO> routes = new ArrayList<>();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        headers.add("Responded", "CartWebController");
        ResponseEntity response;
        try{
            if (Objects.isNull(request.getParameter("arrivalAirport"))) {
                routes = routeService.allRoutes();
            } else{
                RouteRequest routeRequest = new RouteRequest();
                routeRequest.setDepartureAirport(request.getParameter("departureAirport"));
                routeRequest.setArrivalAirport(request.getParameter("arrivalAirport"));
                routes.add(routeService.getRoute(routeRequest));
            }
            response = ResponseEntity.accepted().headers(headers).body(routes);
        } catch(NotFoundException e) {
            response = new ResponseEntity(HttpStatus.NOT_FOUND);
        } catch(Exception e) {
            response = new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        
        return response;
    }

    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity updateRoute(@RequestBody RouteRequest route, @Param("id") long idRoute) {
        ResponseEntity myResponseEntity;
        try {
            routeService.updateRoute(route, idRoute);
            myResponseEntity = new ResponseEntity(HttpStatus.OK);
        } catch (NotFoundException e) {
            myResponseEntity = new ResponseEntity(HttpStatus.IM_USED);
        } catch(Exception e){
            myResponseEntity = new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return  myResponseEntity;
    }

    @RequestMapping(method = RequestMethod.DELETE)
    public ResponseEntity deleteCabin(@RequestBody RouteRequest routeRequest) {
        ResponseEntity myResponseEntity;
        try {
            routeService.deleteRoute(routeRequest);
            myResponseEntity = new ResponseEntity(HttpStatus.OK);
        } catch(NotFoundException e){
            myResponseEntity = new ResponseEntity(HttpStatus.NOT_FOUND);
        } catch(Exception e){
            myResponseEntity = new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return  myResponseEntity;
    }
}
