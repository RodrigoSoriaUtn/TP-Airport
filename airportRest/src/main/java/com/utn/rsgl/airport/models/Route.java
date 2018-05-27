package com.utn.rsgl.airport.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "routes")
@Getter @Setter
public class Route {
    @Id @GeneratedValue
    @Column(name = "PK_idRoute")
    private long id;

    @ManyToOne
    @JoinColumn(name = "FK_departureAirport", referencedColumnName = "PK_idAirport", nullable = false)
    private Airport departureAirport;

    @ManyToOne
    @JoinColumn(name = "FK_arrivalAirport", referencedColumnName = "PK_idAirport", nullable = false)
    private Airport arrivalAirport;

    @OneToMany(mappedBy = "route")
    private List<PricePerCabinPerRoute> prices;


}
