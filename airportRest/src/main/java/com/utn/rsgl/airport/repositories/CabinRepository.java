package com.utn.rsgl.airport.repositories;

import com.utn.rsgl.airport.models.Cabin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CabinRepository extends JpaRepository<Cabin, Long> {
}
