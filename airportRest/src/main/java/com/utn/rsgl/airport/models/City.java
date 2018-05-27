package com.utn.rsgl.airport.models;



import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;


@Entity
@Table(name = "cities")
@Getter @Setter
public class City {
    @Id @GeneratedValue
    @Column(name = "PK_idCity")
    private long id;

    @Column(name = "name", nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "FK_idState",referencedColumnName = "PK_idState", nullable = false)
    private State state;

    @OneToMany(mappedBy = "city")
    private List<Airport> airports;
}
