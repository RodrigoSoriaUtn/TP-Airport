package com.utn.rsgl.airport;


import com.utn.rsgl.airport.repositories.CountryRepository;
import com.utn.rsgl.core.shared.models.test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AirportApplication {

    @Autowired
    static CountryRepository repo;

    public static void main(String[] args) {
        SpringApplication.run(AirportApplication.class, args);

        test testeando = new test();
        testeando.holaMundo();
        System.out.println(testeando.probando);
    }
}
