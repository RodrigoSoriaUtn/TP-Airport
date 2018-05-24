package com.utn.rsgl.airport.models;


import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "airports")
public class Airport {
    @Id @GeneratedValue
    @Column(name = "PK_idAirport")
    private long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "IATA_code", unique = true, nullable = false)
    private String IATAcode;

    @ManyToOne
    @JoinColumn(name = "PK_idCity")
    @Column(name = "FK_idCity", nullable = false)
    private City city;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "departureAirport")
    private List<Route> departureRoutes;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "arrivalAirport")
    private List<Route> arrivalRoutes;

}
