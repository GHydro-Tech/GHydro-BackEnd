package com.ghydrobackend.ghydro.controller;

import com.ghydrobackend.ghydro.model.Setor;
import com.ghydrobackend.ghydro.service.SetorService;

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
@RequestMapping("setor")
@CrossOrigin("*")
public class SetorController {

    @Autowired
    private SetorService setorService;

    @PostMapping
    public Setor salvarSetor(@RequestBody Setor setor){
        return setorService.salvarSetor(setor);
    }

    @GetMapping
    public List<Setor> listarSetor(){
        return setorService.listarSetor();
    }

    @PutMapping
    public Setor atualizarSetor(@RequestBody Setor setor){
        return setorService.atualizarSetor(setor);
    }

    @DeleteMapping("/{id}/")
    public void deletarSetor(@PathVariable Long id){
        setorService.deletarSetor(id);
    }
    
}
