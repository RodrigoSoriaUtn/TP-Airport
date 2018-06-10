package com.utn.rsgl.airport.repositories;

import com.utn.rsgl.airport.models.PricePerCabinPerRoute;
import com.utn.rsgl.airport.models.Route;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PricePerCabinPerRouteRepository extends JpaRepository<PricePerCabinPerRoute, Long> {

    public void update(PricePerCabinPerRoute originalRoute);

    public List<PricePerCabinPerRoute> findPricePerCabinPerRouteByRoute(Route route);
}
