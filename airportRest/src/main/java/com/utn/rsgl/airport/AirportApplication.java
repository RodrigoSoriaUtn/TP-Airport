package com.utn.rsgl.airport;


import com.utn.rsgl.airport.repositories.CountryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AirportApplication {

    @Autowired
    static CountryRepository repo;

    public static void main(String[] args) {
        SpringApplication.run(AirportApplication.class, args);
    }
}
