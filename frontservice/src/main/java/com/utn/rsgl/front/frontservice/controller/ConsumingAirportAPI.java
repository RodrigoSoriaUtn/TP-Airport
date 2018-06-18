package com.utn.rsgl.front.frontservice.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

public class ConsumingAirportAPI<T> {

	RestTemplate restTemplate;

	public ConsumingAirportAPI(){
		this.restTemplate = new RestTemplate();
	}

	public T getEntity(Class entityClass, String url) throws ClassNotFoundException {
		ResponseEntity<T> getResponse = restTemplate.getForEntity(url, entityClass);
		return Optional.ofNullable(getResponse.getBody()).get();
	}
}
