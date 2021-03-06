package com.utn.rsgl.airport.models;

import lombok.Data;
import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "routes")
@Data
public class Route {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "PK_idRoute")
    private long id;

    @ManyToOne
    @JoinColumn(name = "FK_departureAirport", referencedColumnName = "PK_idAirport", nullable = false)
    private Airport departureAirport;

    @ManyToOne
    @JoinColumn(name = "FK_arrivalAirport", referencedColumnName = "PK_idAirport", nullable = false)
    private Airport arrivalAirport;
}
