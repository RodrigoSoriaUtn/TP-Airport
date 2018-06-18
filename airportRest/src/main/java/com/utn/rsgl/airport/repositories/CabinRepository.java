package com.utn.rsgl.airport.repositories;

import com.utn.rsgl.airport.models.Cabin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface CabinRepository extends JpaRepository<Cabin, Long> {

    Cabin findCabinByName(String name);

    @Transactional
    @Modifying
    @Query("update Cabin set name = :name  where id = :idcabin")
    void update(@Param("name") String name, @Param("idcabin") Long id);
}
