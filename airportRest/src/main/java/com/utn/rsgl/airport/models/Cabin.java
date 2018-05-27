package com.utn.rsgl.airport.models;



import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "cabins")
@Getter @Setter
public class Cabin {
    @Id @GeneratedValue
    @Column(name = "PK_idCabin")
    private long id;

    @Column(name = "name", nullable = false)
    private String name;

    @OneToMany(mappedBy = "cabin")
    private List<PricePerCabinPerRoute> prices;
}
