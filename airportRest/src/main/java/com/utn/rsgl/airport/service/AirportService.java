package com.utn.rsgl.airport.service;

import com.utn.rsgl.airport.exceptions.DataAlreadyExistsException;
import com.utn.rsgl.airport.factories.DtoFactory;
import com.utn.rsgl.airport.models.Airport;
import com.utn.rsgl.airport.models.City;
import com.utn.rsgl.airport.repositories.AirportRepository;
import com.utn.rsgl.airport.repositories.CityRepository;
import com.utn.rsgl.airport.requests.AirportRequest;
import com.utn.rsgl.core.shared.dto.AirportDTO;
import javassist.NotFoundException;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
           throw new NotFoundException("city with the IATA code : " + cityIataCode + " wat not found on the Database.");
        }
        if(airportRepository.findAirportByIataCode(airportIataCode) != null){
            throw new DataAlreadyExistsException("the airport with the IATA code : "+airportIataCode+" already exist on the database.");
        }
        Airport airport = new Airport(airportName, airportIataCode);
        airport.setCity(city);
        airportRepository.save(airport);
    }

    public void update(AirportRequest airportRequest, String previousAirportIATACode) throws NotFoundException,
                                                                                            DataAlreadyExistsException {
        Airport airport = airportRepository.findAirportByIataCode(previousAirportIATACode);
        Airport airportWithIata = airportRepository.findAirportByIataCode(airportRequest.getIATACode());
        if(airport == null){
            throw new NotFoundException("The airport with the IATA Code : " + previousAirportIATACode + " doesn't exists");
        }
        if(airportWithIata != null){
            throw new DataAlreadyExistsException("The airport with the new Iata Code :"+airportRequest.getIATACode()+
                                                 " Already exist!");
        }
        airportRepository.update(airportRequest.getName(), airportRequest.getIATACode());
    }

    public List<AirportDTO> listAll(){
        List<Airport> airports = airportRepository.findAll();
        List<AirportDTO> airportDtos = new ArrayList<>();

        for(Airport airport : airports){
            airportDtos.add(DtoFactory.getInstance().getDTOByModel(airport, AirportDTO.class));
        }
        return airportDtos;
    }

    public void remove(AirportRequest airportRequest) throws NotFoundException {
        Airport airport = airportRepository.findAirportByIataCode(airportRequest.getIATACode());
        if(airport == null){
            throw new NotFoundException(" there is no airport with the IATA code :"+airportRequest.getIATACode());
        }
        airportRepository.delete(airport);
    }
}
