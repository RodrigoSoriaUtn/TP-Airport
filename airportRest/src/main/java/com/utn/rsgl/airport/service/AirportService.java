package com.utn.rsgl.airport.service;

import com.utn.rsgl.airport.exceptions.DataAlreadyExistsException;
import com.utn.rsgl.airport.models.Airport;
import com.utn.rsgl.airport.models.City;
import com.utn.rsgl.airport.repositories.AirportRepository;
import com.utn.rsgl.airport.repositories.CityRepository;
import javassist.NotFoundException;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AirportService {

    @Autowired
    @Setter
    private AirportRepository airportRepository;

    @Autowired
    @Setter
    private CityRepository cityRepository;

    /**
     * saves the airport to the database. Checking if there is a city with that IATACode
     * @param airportName
     * @param airportIataCode
     * @param cityIataCode
     * @throws NotFoundException if there is no city with that IATACode.
     * @throws DataAlreadyExistsException if the airport with that IATACode already exists.
     */
    public void save(String airportName, String airportIataCode, String cityIataCode) throws NotFoundException,
                                                                                            DataAlreadyExistsException {
        City city = cityRepository.findByIataCode(cityIataCode);
        if(city == null){
           throw new NotFoundException("city with the IATA code : "+city.getIataCode()+" wat not found on the Database.");
        }
        if(airportRepository.findAirportByIataCode(airportIataCode) == null){
            throw new DataAlreadyExistsException("the airport with the IATA code : "+airportIataCode+" already exist on the database.");
        }
        Airport airport = new Airport();
        airport.setName(airportName);
        airport.setIataCode(airportIataCode);
        airport.setCity(city);
        airportRepository.save(airport);
    }
}
