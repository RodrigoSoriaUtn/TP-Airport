package com.utn.rsgl.airport.models;



import lombok.Data;

import javax.persistence.*;
import java.util.List;


@Entity
@Table(name = "cities")
@Data
public class City {
    @Id @GeneratedValue
    @Column(name = "PK_idCity")
    private long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "IATA_code", nullable = false, unique = true)
    private String iataCode;

    @ManyToOne
    @JoinColumn(name = "FK_idState",referencedColumnName = "PK_idState", nullable = false)
    private State state;

}
