package com.ghydrobackend.ghydro.controller;

import com.ghydrobackend.ghydro.model.EstacaoMetereologica;
import com.ghydrobackend.ghydro.service.EstacaoMetereologicaService;

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
@RequestMapping("estacaoMetereologica")
@CrossOrigin("*")
public class EstacaoMetereologicaController {

    @Autowired
    private EstacaoMetereologicaService estacaoMetereologicaService;

    @PostMapping
    public EstacaoMetereologica salvarEstacaoMetereologica(@RequestBody EstacaoMetereologica estacaoMetereologica){
        return estacaoMetereologicaService.salvarEstacaoMetereologica(estacaoMetereologica);
    }

    @GetMapping
    public List<EstacaoMetereologica> listarEstacaoMetereologica(){
        return estacaoMetereologicaService.listarEstacaoMetereologica();
    }

    @PutMapping
    public EstacaoMetereologica atualizarEstacaoMetereologica(@RequestBody EstacaoMetereologica estacaoMetereologica){
        return estacaoMetereologicaService.atualizarEstacaoMetereologica(estacaoMetereologica);
    }

    @DeleteMapping("/{id}/")
    public void deletarEstacaoMetereologica(@PathVariable Long id){
        estacaoMetereologicaService.deletarEstacaoMetereologica(id);
    }
    
}
