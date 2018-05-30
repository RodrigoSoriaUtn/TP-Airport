package com.utn.rsgl.airport.models;

import lombok.Data;
import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "countries")
@Data
public class Country {
    @Id @GeneratedValue
    @Column(name = "PK_idCountry")
    private long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "ISO_code3", nullable = false, unique = true)
    private String ISOCode3;

}
