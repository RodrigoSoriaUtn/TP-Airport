package com.utn.rsgl.airport.service;

import com.utn.rsgl.airport.factories.DtoFactory;
import com.utn.rsgl.airport.models.Cabin;
import com.utn.rsgl.airport.repositories.CabinRepository;
import com.utn.rsgl.airport.requests.CabinRequest;
import com.utn.rsgl.core.shared.dto.CabinDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CabinService {

    @Autowired
    private  CabinRepository cabinRepository;

    public void saveCabin(CabinRequest cabin){
        cabinRepository.save(new Cabin(cabin.getName()));
    }

    public CabinDTO getCabin(String cabin){
        return DtoFactory.getInstance().getDTOByModel(cabinRepository.findCabinByName(cabin), CabinDTO.class);
    }

    public void deleteCabin(CabinRequest cabin){
        cabinRepository.delete(cabinRepository.findCabinByName(cabin.getName()));
    }

    public void updateCabin(CabinRequest cabin){
        Cabin theCabin = cabinRepository.findCabinByName(cabin.getName());
        cabinRepository.update(theCabin.getName(), theCabin.getId());
    }

    public List<CabinDTO> getAll(){
        return DtoFactory.getInstance().getDTOByModel( cabinRepository.findAll(), CabinDTO.class);
    }

}
