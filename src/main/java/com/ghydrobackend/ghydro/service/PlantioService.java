package com.ghydrobackend.ghydro.service;

import com.ghydrobackend.ghydro.model.Plantio;
import com.ghydrobackend.ghydro.repository.PlantioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlantioService {

    @Autowired
    private PlantioRepository plantioRepository;

    public Plantio buscarPorId(Long id) {
        return plantioRepository.findById(id).orElseThrow(() -> new RuntimeException("Plantio não encontrado com o ID: " + id));
    }

    public Plantio salvarPlantio(Plantio plantio) {
        return plantioRepository.save(plantio);
    }

    public List<Plantio> listarPlantio() {
        return plantioRepository.findAll();
    }

    public Plantio atualizarPlantio(Plantio plantio) {
        return plantioRepository.save(plantio);
    }

    public void deletarPlantio(Long id) {
        plantioRepository.deleteById(id);
    }
}
