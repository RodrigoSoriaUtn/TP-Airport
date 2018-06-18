package com.utn.rsgl.airport.repositories;

import com.utn.rsgl.airport.models.Airport;
import com.utn.rsgl.airport.models.Route;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface RouteRepository extends JpaRepository<Route, Long> {

    public Route findRouteByArrivalAirportAndDepartureAirport(Airport arrival, Airport departure);

    @Transactional
    @Modifying
    @Query("update Route " +
            "set arrivalAirport = :arrival, departureAirport = :departure " +
            " where id = :oldId")
    public void update(@Param("oldId") Long oldId, @Param("arrival") Airport arrivalAirport, @Param("departure") Airport departureAirport);

}
