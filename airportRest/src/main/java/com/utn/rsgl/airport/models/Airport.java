package com.utn.rsgl.airport.models;


import lombok.Data;
import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "airports")
@Data
public class Airport {
    @Id @GeneratedValue
    @Column(name = "PK_idAirport")
    private long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "IATA_code", unique = true, nullable = false)
    private String IATACode;

    @ManyToOne
    @JoinColumn(name = "FK_idCity", referencedColumnName = "PK_idCity", nullable = false)
    private City city;

    @OneToMany(mappedBy = "departureAirport")
    private List<Route> departureRoutes;

    @OneToMany(mappedBy = "arrivalAirport")
    private List<Route> arrivalRoutes;

}
