package com.utn.rsgl.airport.controllers;

import com.utn.rsgl.airport.config.AccessVerifier;
import com.utn.rsgl.airport.requests.PricePerCabinPerRouteRequest;
import com.utn.rsgl.airport.service.PricePerCabinPerRouteService;
import com.utn.rsgl.core.shared.dto.PricePerCabinPerRouteDTO;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping(value = "/travels", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class PricePerCabinPerRouteController {

    @Autowired
    private PricePerCabinPerRouteService priceService;

    @RequestMapping(method = RequestMethod.POST)
    public @ResponseBody ResponseEntity save(@RequestBody PricePerCabinPerRouteRequest request){
        ResponseEntity status;
        if(AccessVerifier.hasPermission()){
            priceService.save(request);
            status = new ResponseEntity(HttpStatus.ACCEPTED);
        }else{
            status = new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
        return status;
    }

    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity update(@RequestBody PricePerCabinPerRouteRequest request, HttpServletRequest httpRequest){
        ResponseEntity status;
        try{
            if(!Objects.isNull(httpRequest.getParameter("routeId"))){
                if(AccessVerifier.hasPermission()){
                    priceService.update(request, Long.getLong(httpRequest.getParameter("routeId")));
                    status = new ResponseEntity(HttpStatus.ACCEPTED);
                }else{
                    status = new ResponseEntity(HttpStatus.UNAUTHORIZED);
                }
            }else{
                status = new ResponseEntity(HttpStatus.BAD_REQUEST);
            }
        }catch (NotFoundException e){
            e.printStackTrace();
            status = new ResponseEntity(HttpStatus.NOT_FOUND);
        } catch(Exception e){
            e.printStackTrace();
            status = new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return status;
    }

    @RequestMapping(method = RequestMethod.DELETE)
    public ResponseEntity delete(@RequestBody PricePerCabinPerRouteRequest request){
        ResponseEntity status;
        try{
            if(AccessVerifier.hasPermission()){
                priceService.delete(request);
                status = new ResponseEntity(HttpStatus.ACCEPTED);
            }else{
                status = new ResponseEntity(HttpStatus.UNAUTHORIZED);
            }
        } catch(Exception e){
            e.printStackTrace();
            status = new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return status;
    }

    @RequestMapping(method = RequestMethod.GET)
    public @ResponseBody ResponseEntity<List<PricePerCabinPerRouteDTO>> get(HttpServletRequest request){
        List<PricePerCabinPerRouteDTO> pricesDtos;
        ResponseEntity response;
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        headers.add("response", "AirportController");
        String arrivalIataCode = request.getParameter("arrivalIataCode");
        String departureIataCode = request.getParameter("departureIataCode");
        String from = request.getParameter("from");
        String to = request.getParameter("to");
        boolean betweenDates = Objects.nonNull(from) && Objects.nonNull(to);
        boolean betweenAirports = Objects.nonNull(arrivalIataCode) && Objects.nonNull(departureIataCode);
        try{
            if(betweenDates && betweenAirports){
                pricesDtos = priceService.getByRouteAndDates(arrivalIataCode, departureIataCode, from, to);
            }else if (betweenAirports){
                pricesDtos = priceService.getByRoute(arrivalIataCode, departureIataCode);
            }else if (betweenDates){
                pricesDtos = priceService.getByDates(from, to);
            }else{
                pricesDtos = priceService.listAll();
            }
            response = ResponseEntity.accepted().headers(headers).body(pricesDtos);
        } catch (Exception e){
            e.printStackTrace();
            response = new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return response;
    }

}
