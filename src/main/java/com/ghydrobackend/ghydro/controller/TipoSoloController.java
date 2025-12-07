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

    @PutMapping("/{id}")
    public TipoSolo atualizarCultura(@PathVariable Long id, @RequestBody TipoSolo tipoSolo) {
        // Agora passamos o ID da URL e o Objeto do Body para a Service
        return tipoSoloService.atualizarTipoSolo(id, tipoSolo);
    }

    @DeleteMapping("/{id}/")
    public void deletarTipoSolo(@PathVariable Long id){
        tipoSoloService.deletarTipoSolo(id);
    }
    
}
