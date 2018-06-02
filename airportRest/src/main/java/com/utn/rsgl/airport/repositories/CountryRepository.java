package com.utn.rsgl.airport.repositories;

import com.utn.rsgl.airport.models.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CountryRepository extends JpaRepository<Country, Long> {
    public Country findCountryByIsoCode3(String isoCode3);
    // TODO : UPDATE QUERY
    @Query("")
    public void update(String previousIsoCode3, String name, String newIsoCode3);
}
