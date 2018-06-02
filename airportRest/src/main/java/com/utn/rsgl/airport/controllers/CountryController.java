package com.utn.rsgl.airport.controllers;

import com.utn.rsgl.airport.config.AccessVerifier;
import com.utn.rsgl.airport.exceptions.DataAlreadyExistsException;
import com.utn.rsgl.airport.requests.CountryRequest;
import com.utn.rsgl.airport.service.CountryService;
import com.utn.rsgl.core.shared.dto.CountryDTO;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/countries", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class CountryController{

    @Autowired
    private CountryService countryService;

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public @ResponseBody ResponseEntity saveCountry(@RequestBody CountryRequest countryRequest) {
        ResponseEntity status = null;
        try {
            if(AccessVerifier.isLogued() && AccessVerifier.hasPermission())
                countryService.save(countryRequest.getName(), countryRequest.getISOCode3());
            else
                status = new ResponseEntity(HttpStatus.UNAUTHORIZED);
        } catch (DataAlreadyExistsException e) {
            status = new ResponseEntity(HttpStatus.IM_USED);
        }

        if(status == null){
            new ResponseEntity(HttpStatus.CREATED);
        }
        return status;
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public @ResponseBody ResponseEntity updateCountry(@RequestBody CountryRequest countryRequest,@PathVariable("previousIsoCode3") String previousIsoCode3){
        ResponseEntity status = null;
        try {
            if(AccessVerifier.isLogued() && AccessVerifier.hasPermission()) {
                countryService.update(previousIsoCode3, countryRequest.getName(), countryRequest.getISOCode3());
            }else{
                status = new ResponseEntity(HttpStatus.UNAUTHORIZED);
            }
        } catch (NotFoundException e) {
            e.printStackTrace();
            status = new ResponseEntity(HttpStatus.NOT_FOUND);
        } catch (Exception e){
            e.printStackTrace();
            status = new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if(status == null){
            status = new ResponseEntity(HttpStatus.OK);
        }
        return status;
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public @ResponseBody ResponseEntity listCountries(){
        List<CountryDTO> countries = countryService.findAll();
        return new ResponseEntity(countries, HttpStatus.OK);
    }

    @RequestMapping(value = "/remove", method = RequestMethod.POST)
    public @ResponseBody ResponseEntity removeCountry(@RequestBody CountryRequest countryRequest){
        ResponseEntity status = null;
        try {
            if (AccessVerifier.isLogued() && AccessVerifier.hasPermission()) {
                countryService.remove(countryRequest.getName(), countryRequest.getISOCode3());
            } else {
                status = new ResponseEntity(HttpStatus.UNAUTHORIZED);
            }
        } catch (NotFoundException e){
            e.printStackTrace();
            status = new ResponseEntity(HttpStatus.NOT_FOUND);
        } catch (Exception e){
            e.printStackTrace();
            status = new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if(status == null){
            status = new ResponseEntity(HttpStatus.OK);
        }
        return status;
    }

    @RequestMapping(value = "/getByISO3/{isoCode}", method = RequestMethod.GET)
    public @ResponseBody ResponseEntity<CountryDTO> getCountryByIsoCode(@PathVariable("isoCode") String isoCode){
        CountryDTO country = countryService.findByIsoCode(isoCode);
        return  new ResponseEntity(country, HttpStatus.OK);
    }

    @RequestMapping(value = "/getByIATA/{iataCode}", method = RequestMethod.GET)
    public @ResponseBody ResponseEntity<CountryDTO> getCountryByAirportIATACode(@PathVariable("iataCode") String iataCode){
        CountryDTO country = countryService.findByAirportIATACode(iataCode);
        return new ResponseEntity(country, HttpStatus.OK);
    }

}
