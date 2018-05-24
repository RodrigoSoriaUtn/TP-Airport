package com.utn.rsgl.airport.models;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "states")
public class State {
    @Id @GeneratedValue
    @Column(name = "PK_idState")
    private long id;

    @Column(name = "name")
    private String name;

    @ManyToOne
    @JoinColumn(name = "PK_idCountry")
    @Column(name = "FK_idCountry")
    private Country country;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "FK_idState")
    @OrderColumn(name = "name")
    private List<City> cities;
}
