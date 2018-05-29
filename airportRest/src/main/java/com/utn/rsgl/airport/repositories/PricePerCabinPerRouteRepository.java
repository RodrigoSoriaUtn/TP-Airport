package com.utn.rsgl.airport.repositories;

import com.utn.rsgl.airport.models.PricePerCabinPerRoute;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PricePerCabinPerRouteRepository extends JpaRepository<PricePerCabinPerRoute, Long> {
}
