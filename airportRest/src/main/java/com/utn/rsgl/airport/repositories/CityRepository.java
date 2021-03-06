package com.utn.rsgl.airport.repositories;

import com.utn.rsgl.airport.models.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CityRepository extends JpaRepository<City, Long> {

    public City findByIataCode(String iataCode);
}
