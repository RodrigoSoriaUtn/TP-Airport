package com.utn.rsgl.airport.controllers;

import com.utn.rsgl.airport.config.AccessVerifier;
import com.utn.rsgl.airport.exceptions.DataAlreadyExistsException;
import com.utn.rsgl.airport.requests.AirportRequest;
import com.utn.rsgl.airport.service.AirportService;
import com.utn.rsgl.core.shared.dto.AirportDTO;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/aiports", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class AirportController{

    @Autowired
    AirportService airportService;

    @RequestMapping(method = RequestMethod.POST)
    public @ResponseBody ResponseEntity save( @RequestBody AirportRequest airport) {
        ResponseEntity status = null ;
        try {
            if(AccessVerifier.isLogued() && AccessVerifier.hasPermission()){
                airportService.save(airport);
            }else {
                status = new ResponseEntity(HttpStatus.UNAUTHORIZED);
            }
        } catch (NotFoundException e) {
            e.printStackTrace();
            status = new ResponseEntity(HttpStatus.NOT_FOUND);
        } catch (DataAlreadyExistsException e) {
            e.printStackTrace();
            status = new ResponseEntity(HttpStatus.IM_USED);
        } catch (Exception e){
            e.printStackTrace();
            status = new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if(status == null) status = new ResponseEntity(HttpStatus.CREATED);
        return status;
    }

    @RequestMapping(value = "/{previousIataCode}", method = RequestMethod.PUT)
    public ResponseEntity update(@RequestBody AirportRequest airportRequest, @PathVariable("previousIataCode") String iataCode){
        ResponseEntity status = null;
        try{
           airportService.update(airportRequest, iataCode);
        } catch (DataAlreadyExistsException e) {
            e.printStackTrace();
            status = new ResponseEntity(HttpStatus.IM_USED);
        } catch (NotFoundException e) {
            e.printStackTrace();
            status = new ResponseEntity(HttpStatus.NOT_FOUND);
        } catch (Exception e){
            e.printStackTrace();
            status = new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if(status == null) status = new ResponseEntity(HttpStatus.OK);
        return status;
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<AirportDTO>> list(){
        List<AirportDTO> airportDtos = airportService.listAll();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        headers.add("response", "AirportController");


        return ResponseEntity.accepted().headers(headers).body(airportDtos);
    }

    @RequestMapping(method = RequestMethod.DELETE)
    public ResponseEntity delete(@RequestBody AirportRequest airportRequest){
        ResponseEntity status = null;
        try {
            airportService.remove(airportRequest);
        } catch (NotFoundException e) {
            e.printStackTrace();
            status = new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        if(status == null) status = new ResponseEntity(HttpStatus.OK);
        return status;
    }

}
