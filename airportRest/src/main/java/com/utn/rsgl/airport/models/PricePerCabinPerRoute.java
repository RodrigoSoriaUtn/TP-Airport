package com.utn.rsgl.airport.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "pricePerCabinPerRoute")
@Getter @Setter
public class PricePerCabinPerRoute {
    @Id @GeneratedValue
    @Column(name = "PK_idPricePerCabinPerRoute")
    private long id;

    @ManyToOne
    @JoinColumn(name = "FK_idRoute", referencedColumnName = "PK_idRoute", nullable = false)
    private Route route;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "vigencyFrom", nullable = false)
    private Date vigencyFrom;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "vigencyTo", nullable = false)
    private Date vigencyTo;

    @ManyToOne
    @JoinColumn(name = "FK_idCabin", referencedColumnName = "PK_idCabin", nullable = false)
    private Cabin cabin;

    @Column(name = "price", nullable = false)
    private Double price;

    @Column(name = "active", nullable = false)
    private boolean active;
}
