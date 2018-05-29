package com.utn.rsgl.airport.models;



import lombok.Data;
import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "cabins")
@Data
public class Cabin {
    @Id @GeneratedValue
    @Column(name = "PK_idCabin")
    private long id;

    @Column(name = "name", nullable = false)
    private String name;

    @OneToMany(mappedBy = "cabin")
    private List<PricePerCabinPerRoute> prices;
}
