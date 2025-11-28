package com.ghydrobackend.ghydro.controller;

import com.ghydrobackend.ghydro.model.Plantio;
import com.ghydrobackend.ghydro.service.PlantioService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("plantio")
@CrossOrigin("*")
public class PlantioController {

    @Autowired
    private PlantioService plantioService;

    @PostMapping
    public Plantio salvarPlantio(@RequestBody Plantio plantio){
        return plantioService.salvarPlantio(plantio);
    }

    @GetMapping
    public List<Plantio> listarPlantio(){
        return plantioService.listarPlantio();
    }

    @PutMapping
    public Plantio atualizarPlantio(@RequestBody Plantio plantio){
        return plantioService.atualizarPlantio(plantio);
    }

    @DeleteMapping("/{id}/")
    public void deletarPlantio(@PathVariable Long id){
        plantioService.deletarPlantio(id);
    }
    
}
