package com.utn.rsgl.airport;


import com.utn.rsgl.core.shared.models.test;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AirportApplication {

    public static void main(String[] args) {
        SpringApplication.run(AirportApplication.class, args);

        test testeando = new test();
        testeando.holaMundo();
        System.out.println(testeando.probando);
    }
}
