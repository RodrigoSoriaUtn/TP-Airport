package com.utn.rsgl.airport.models;

import lombok.Data;
import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "states")
@Data
public class State {
    @Id @GeneratedValue
    @Column(name = "PK_idState")
    private long id;

    @Column(name = "name")
    private String name;

    @ManyToOne
    @JoinColumn(name = "FK_idCountry", referencedColumnName = "PK_idCountry", nullable = false)
    private Country country;

    @OneToMany(mappedBy = "state")
    private List<City> cities;
}
