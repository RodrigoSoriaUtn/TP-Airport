package com.utn.rsgl.airport.models;



import javax.persistence.*;
import java.util.List;


@Entity
@Table(name = "cities")
public class City {
    @Id @GeneratedValue
    @Column(name = "PK_idCity")
    private long id;

    @Column(name = "name", nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "PK_idState")
    @Column(name = "FK_idState", nullable = false)
    private State state;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "FK_idCity")
    private List<Airport> airports;
}
