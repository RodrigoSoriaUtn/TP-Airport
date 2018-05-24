package com.utn.rsgl.airport.models;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "routes")
public class Route {
    @Id @GeneratedValue
    @Column(name = "PK_idRoute")
    private long id;

    @ManyToOne
    @JoinColumn(name = "PK_idAirport")
    @Column(name = "departureAirport", nullable = false)
    private Airport departureAirport;

    @ManyToOne
    @JoinColumn(name = "PK_idAirport")
    @Column(name = "arrivalAirport", nullable = false)
    private Airport arrivalAirport;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "FK_idRoute")
    private List<PricePerCabinPerRoute> prices;


}
