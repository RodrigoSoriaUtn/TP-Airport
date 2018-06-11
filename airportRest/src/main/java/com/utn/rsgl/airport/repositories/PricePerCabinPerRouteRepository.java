package com.utn.rsgl.airport.repositories;

import com.utn.rsgl.airport.models.Airport;
import com.utn.rsgl.airport.models.Cabin;
import com.utn.rsgl.airport.models.PricePerCabinPerRoute;
import com.utn.rsgl.airport.models.Route;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface PricePerCabinPerRouteRepository extends JpaRepository<PricePerCabinPerRoute, Long> {

    @Query("UPDATE PricePerCabinPerRoute " +
            " SET active = :active, price = :price, route = :route, cabin = :cabin, " +
            "       vigencyFrom = :vigencyFrom, vigencyTo = :vigencyTo " +
            "WHERE id = :id")
    public void update(@Param("active") boolean active, @Param("price") Double price, @Param("route") Route route,
                       @Param("cabin") Cabin cabin, @Param("vigencyFrom") Date vigencyFrom,
                       @Param("vigencyTo") Date vigencyTo, @Param("id") Long id);

    public List<PricePerCabinPerRoute> findPricePerCabinPerRouteByRoute(Route route);
}
