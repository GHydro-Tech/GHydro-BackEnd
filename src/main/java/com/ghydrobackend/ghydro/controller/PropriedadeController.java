package com.ghydrobackend.ghydro.controller;

import com.ghydrobackend.ghydro.model.Propriedade;
import com.ghydrobackend.ghydro.model.Proprietario;
import com.ghydrobackend.ghydro.service.PropriedadeService;

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
@RequestMapping("propriedade")
@CrossOrigin("*")
public class PropriedadeController {

    @Autowired
    private PropriedadeService propriedadeService;

    @PostMapping
    public Propriedade salvarPropriedade(@RequestBody Propriedade propriedade){
        return propriedadeService.salvarPropriedade(propriedade);
    }

    @GetMapping
    public List<Propriedade> listarPropriedade(){
        return propriedadeService.listarPropriedade();
    }

    @PutMapping
    public Propriedade atualizarPropriedade(@RequestBody Propriedade propriedade){
        return propriedadeService.atualizarPropriedade(propriedade);
    }

    @DeleteMapping("/{id}/")
    public void deletarPropriedade(@PathVariable Long id){
        propriedadeService.deletarPropriedade(id);
    }
    
}
