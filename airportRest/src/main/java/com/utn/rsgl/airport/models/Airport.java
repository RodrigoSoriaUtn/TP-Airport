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
    private String iataCode;

    @ManyToOne
    @JoinColumn(name = "FK_idCity", referencedColumnName = "PK_idCity", nullable = false)
    private City city;


    public Airport(){}

    public Airport(String airportName, String airportIataCode) {
        this.setName(airportName);
        this.setIataCode(airportIataCode);
        this.setCity(null);
    }

}
