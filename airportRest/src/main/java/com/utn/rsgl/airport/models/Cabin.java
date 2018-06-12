package com.utn.rsgl.airport.models;



import lombok.Data;
import javax.persistence.*;

@Entity
@Table(name = "cabins")
@Data
public class Cabin {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "PK_idCabin")
    private long id;

    @Column(name = "name", nullable = false)
    private String name;

    public Cabin(){}
    public Cabin(String name) {
        this.name = name;
    }
}
