package com.utn.rsgl.airport.factories;

import org.modelmapper.ModelMapper;
import java.lang.reflect.Type;

public class DtoFactory {

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

    private void init(){
        mapper = new ModelMapper();
    }

    public <D> D getDTOByModel(Object source, Type destinationType) {
        return mapper.map(source, destinationType);
    }


}
