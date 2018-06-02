package com.utn.rsgl.airport.controllers;

import com.utn.rsgl.airport.config.AccessVerifier;
import com.utn.rsgl.airport.exceptions.DataAlreadyExistsException;
import com.utn.rsgl.airport.requests.AirportRequest;
import com.utn.rsgl.airport.service.AirportService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/aiports", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class AirportController{

    @Autowired
    AirportService airportService;

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public @ResponseBody ResponseEntity save( @RequestBody AirportRequest airport) {
        ResponseEntity status = null ;
        try {
            if(AccessVerifier.isLogued() && AccessVerifier.hasPermission())
                airportService.save(airport.getName(), airport.getIATACode(), airport.getCityIATACode());
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
        if(status == null) {
            status = new ResponseEntity(HttpStatus.CREATED);
        }
        return status;
    }

}
