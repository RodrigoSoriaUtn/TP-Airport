package com.utn.rsgl.airport.models;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "countries")
public class Country {
    @Id @GeneratedValue
    @Column(name = "PK_idCountry")
    private long id;

    @Column(name = "name")
    private String name;

    @Column(name = "ISO_code3")
    private String ISOcode3;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "FK_idCountry")
    @OrderColumn(name = "name")
    private List<State> states;
}
