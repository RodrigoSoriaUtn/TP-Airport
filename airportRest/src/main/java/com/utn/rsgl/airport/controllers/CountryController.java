package com.utn.rsgl.airport.controllers;

import com.utn.rsgl.airport.models.Country;
import com.utn.rsgl.airport.repositories.CountryRepository;
import com.utn.rsgl.airport.requests.CountryRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/cities", produces = MediaType.APPLICATION_JSON_VALUE)
public class CountryController {

    @Autowired
    private CountryRepository countryRepository;

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public @ResponseBody ResponseEntity save(@RequestBody CountryRequest countryRequest) {

        Country country = new Country();
        country.setName(countryRequest.getName());
        country.setISOCode3(countryRequest.getISOCode3());

        countryRepository.save(country);

        return new ResponseEntity(HttpStatus.CREATED);
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public @ResponseBody ResponseEntity listCountries(){
        List<Country> countries = countryRepository.findAll();

        return new ResponseEntity(countries, HttpStatus.OK);
    }

}
