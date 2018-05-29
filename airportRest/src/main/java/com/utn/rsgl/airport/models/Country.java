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

    @Column(name = "name")
    private String name;

    @Column(name = "ISO_code3")
    private String ISOcode3;

    @OneToMany(mappedBy = "country")
    private List<State> states;
}
