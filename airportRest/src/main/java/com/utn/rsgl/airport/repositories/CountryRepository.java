package com.utn.rsgl.airport.repositories;

import com.utn.rsgl.airport.models.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CountryRepository extends JpaRepository<Country, Long> {
    public Country findCountryByIsoCode3(String isoCode3);

    @Query("UPDATE Country " +
            " SET name = :name, isoCode3 = :newCode " +
            "WHERE isoCode3 = :previousCode")
    public void update(@Param("previousCode") String previousIsoCode3, @Param("name") String name,
                       @Param("newCode") String newIsoCode3);
}
