package com.utn.rsgl.airport.models;

import lombok.Data;
import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "countries")
@Data
public class Country {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "PK_idCountry")
    private long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "ISO_code3", nullable = false, unique = true)
    private String isoCode3;

    public Country(String country, String iso) {
        this.setName(country);
        this.setIsoCode3(iso);
    }

    public Country() {}
}
