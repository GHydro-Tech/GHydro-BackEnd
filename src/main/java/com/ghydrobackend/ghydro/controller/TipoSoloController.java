package com.ghydrobackend.ghydro.controller;

import com.ghydrobackend.ghydro.model.TipoSolo;
import com.ghydrobackend.ghydro.service.TipoSoloService;

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
@RequestMapping("tipoSolo")
@CrossOrigin("*")
public class TipoSoloController {

    @Autowired
    private TipoSoloService tipoSoloService;

    @PostMapping
    public TipoSolo salvarTipoSolo(@RequestBody TipoSolo tipoSolo){
        return tipoSoloService.salvarTipoSolo(tipoSolo);
    }

    @GetMapping
    public List<TipoSolo> listarTipoSolo(){
        return tipoSoloService.listarTipoSolo();
    }

    @PutMapping
    public TipoSolo atualizarTipoSolo(@RequestBody TipoSolo tipoSolo){
        return tipoSoloService.atualizarTipoSolo(tipoSolo);
    }

    @DeleteMapping("/{id}/")
    public void deletarTipoSolo(@PathVariable Long id){
        tipoSoloService.deletarTipoSolo(id);
    }
    
}
