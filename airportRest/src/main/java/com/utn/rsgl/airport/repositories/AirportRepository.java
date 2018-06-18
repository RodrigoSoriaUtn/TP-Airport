package com.utn.rsgl.airport.repositories;

import com.utn.rsgl.airport.models.Airport;
import com.utn.rsgl.airport.models.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface AirportRepository extends JpaRepository<Airport, Long> {

    public Airport findAirportByIataCode(String iataCode);

    @Transactional
    @Modifying
    @Query("update Airport" +
            " set name = :name, iataCode = :iataCode, city = :city " +
            " where id = :idAirport ")
    public Airport update(@Param("idAirport") Long id, @Param("name") String name, @Param("iataCode") String iataCode,
                          @Param("city") City city);
}
