package com.utn.rsgl.airport.factories;

import com.utn.rsgl.airport.models.Country;
import com.utn.rsgl.core.shared.dto.CountryDTO;
import org.modelmapper.ModelMapper;

import java.lang.reflect.Type;


public class DtoFactory implements FactoryProcesser{

    private static DtoFactory factory;
    private static ModelMapper mapper;

    public static DtoFactory getInstance(){
        if(factory == null){
            factory = new DtoFactory();
        }
        return factory;
    }
    private DtoFactory(){
        init();
    }

    public <D> D getDTOByModel(Object source, Type destinationType) {
        return mapper.map(source, destinationType);
    }

    private void init(){
        mapper = new ModelMapper();
    }


}
