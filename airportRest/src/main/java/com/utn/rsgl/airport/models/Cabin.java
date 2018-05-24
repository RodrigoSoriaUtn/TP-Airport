package com.utn.rsgl.airport.models;



import javax.persistence.*;

@Entity
@Table(name = "cabins")
public class Cabin {
    @Id @GeneratedValue
    @Column(name = "PK_idCabin")
    private long id;

    @Column(name = "name", nullable = false)
    private String name;
}
