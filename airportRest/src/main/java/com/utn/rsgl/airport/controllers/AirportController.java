package com.utn.rsgl.airport.controllers;

import com.utn.rsgl.airport.models.Airport;
import com.utn.rsgl.airport.repositories.AirportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/aiports", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class AirportController {

    @Autowired
    private AirportRepository airportRepository;

    public void setAirportRepository(AirportRepository airportRepository) {
        this.airportRepository = airportRepository;
    }

    @RequestMapping(value = "/save", method = RequestMethod.PUT)
    public @ResponseBody HttpStatus save(@PathVariable("airportName") String airportName,
                                         @PathVariable("airportIATACode") String airportIataCode,
                                         @PathVariable("cityIATACode") String cityIATACode) {
        Airport airport = new Airport();

    }
}
