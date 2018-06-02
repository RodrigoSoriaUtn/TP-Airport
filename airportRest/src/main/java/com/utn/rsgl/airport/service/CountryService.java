package com.utn.rsgl.airport.service;

import com.utn.rsgl.airport.exceptions.DataAlreadyExistsException;
import com.utn.rsgl.airport.factories.DtoFactory;
import com.utn.rsgl.airport.models.Country;
import com.utn.rsgl.airport.repositories.AirportRepository;
import com.utn.rsgl.airport.repositories.CountryRepository;
import com.utn.rsgl.core.shared.dto.CountryDTO;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CountryService {

    private final String notFoundMessage = "cant find the country with the specified isoCode3";

    @Autowired
    private CountryRepository countryRepository;

    @Autowired
    private AirportRepository airportRepository;

    public void save(String name, String isoCode3) throws DataAlreadyExistsException {
        if(countryRepository.findCountryByIsoCode3(isoCode3) != null){
            throw new DataAlreadyExistsException("The city with the ISO code : "
                                                +isoCode3+" already exists!");
        }
        Country country = new Country();
        country.setName(name);
        country.setIsoCode3(isoCode3);
        countryRepository.save(country);
    }

    public List<CountryDTO> findAll() {
        List<Country> countries = countryRepository.findAll();
        List<CountryDTO> countriesDTO = new ArrayList<>();
        DtoFactory factory = DtoFactory.getInstance();

        for(Country country : countries){
            countriesDTO.add(factory.getDTOByModel(country, CountryDTO.class));
        }

        return countriesDTO;
    }

    public CountryDTO findByAirportIATACode(String iataCode) {
        DtoFactory factory = DtoFactory.getInstance();
        Country country = airportRepository.findAirportByIataCode(iataCode).getCity().getState().getCountry();
        return factory.getDTOByModel(country, CountryDTO.class);
    }

    public CountryDTO findByIsoCode(String isoCode) {
        DtoFactory factory = DtoFactory.getInstance();
        Country country = countryRepository.findCountryByIsoCode3(isoCode);
        return factory.getDTOByModel(country, CountryDTO.class);
    }

    public void update(String name, String originalIsoCode3, String newIsoCode3) throws NotFoundException {
        Country originalCountry = countryRepository.findCountryByIsoCode3(newIsoCode3);
        if(originalCountry == null){
            throw new NotFoundException(notFoundMessage);
        }else {
            countryRepository.update(originalIsoCode3, name, newIsoCode3);
        }
    }

    public void remove(String name, String isoCode3) throws NotFoundException {
        Country originalCountry = countryRepository.findCountryByIsoCode3(isoCode3);
        if(originalCountry == null){
            throw new NotFoundException(notFoundMessage);
        }else {
            countryRepository.delete(originalCountry);
        }
    }
}
