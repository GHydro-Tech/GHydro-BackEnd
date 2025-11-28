package com.ghydrobackend.ghydro.controller;

import com.ghydrobackend.ghydro.model.Proprietario;
import com.ghydrobackend.ghydro.service.ProprietarioService;

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
@RequestMapping("proprietario")
@CrossOrigin("*")
public class ProprietarioController {

    @Autowired
    private ProprietarioService proprietarioService;

    @PostMapping
    public Proprietario salvarProprietario(@RequestBody Proprietario proprietario){
        return proprietarioService.salvarProprietario(proprietario);
    }

    @GetMapping
    public List<Proprietario> listarProprietario(){
        return proprietarioService.listarProprietario();
    }

    @PutMapping
    public Proprietario atualizarProprietario(@RequestBody Proprietario proprietario){
        return proprietarioService.atualizarProprietario(proprietario);
    }

    @DeleteMapping("/{id}/")
    public void deletarProprietario(@PathVariable Long id){
        proprietarioService.deletarProprietario(id);
    }
    
}
