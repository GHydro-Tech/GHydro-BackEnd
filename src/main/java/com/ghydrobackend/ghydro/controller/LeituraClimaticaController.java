package com.ghydrobackend.ghydro.controller;

import com.ghydrobackend.ghydro.model.LeituraClimatica;
import com.ghydrobackend.ghydro.service.LeituraClimaticaService;

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
@RequestMapping("leituraClimatica")
@CrossOrigin("*")
public class LeituraClimaticaController {

    @Autowired
    private LeituraClimaticaService leituraClimaticaService;

    @PostMapping
    public LeituraClimatica salvarLeituraClimatica(@RequestBody LeituraClimatica leituraClimatica){
        return leituraClimaticaService.salvarLeituraClimatica(leituraClimatica);
    }

    @GetMapping
    public List<LeituraClimatica> listarLeituraClimatica(){
        return leituraClimaticaService.listarLeituraClimatica();
    }

    @PutMapping
    public LeituraClimatica atualizarLeituraClimatica(@RequestBody LeituraClimatica leituraClimatica){
        return leituraClimaticaService.atualizarLeituraClimatica(leituraClimatica);
    }

    @DeleteMapping("/{id}/")
    public void deletarLeituraClimatica(@PathVariable Long id){
        leituraClimaticaService.deletarLeituraClimatica(id);
    }
    
}
