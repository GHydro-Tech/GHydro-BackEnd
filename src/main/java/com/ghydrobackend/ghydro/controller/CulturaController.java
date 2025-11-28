package com.ghydrobackend.ghydro.controller;

import com.ghydrobackend.ghydro.model.Cultura;
import com.ghydrobackend.ghydro.service.CulturaService;

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
@RequestMapping("cultura")
@CrossOrigin("*")
public class CulturaController {
    
    @Autowired
    private CulturaService culturaService;

    @PostMapping
    public Cultura salvarCultura(@RequestBody Cultura cultura){
        return culturaService.salvarCultura(cultura);
    }

    @GetMapping
    public List<Cultura> listarCultura(){
        return culturaService.listarCultura();
    }

    @PutMapping
    public Cultura atualizarCultura(@RequestBody Cultura cultura){
        return culturaService.atualizarCultura(cultura);
    }

    @DeleteMapping("/{id}/")
    public void deletarCultura(@PathVariable Long id){
        culturaService.deletarCultura(id);
    }
}
