package com.ghydrobackend.ghydro.controller;

import com.ghydrobackend.ghydro.model.EstadoFenologico;
import com.ghydrobackend.ghydro.service.EstadoFenologicoService;

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
@RequestMapping("estadoFenologico")
@CrossOrigin("*")
public class EstadoFenologicoController {

    @Autowired
    private EstadoFenologicoService estadoFenologicoService;

    @PostMapping
    public EstadoFenologico salvarEstadoFenologico(@RequestBody EstadoFenologico estadoFenologico){
        return estadoFenologicoService.salvarEstadoFenologico(estadoFenologico);
    }

    @GetMapping
    public List<EstadoFenologico> listarEstadoFenologico(){
        return estadoFenologicoService.listarEstadoFenologico();
    }

    @PutMapping("/{id}")
    public EstadoFenologico atualizarEstadoFenologico(@PathVariable Long id, @RequestBody EstadoFenologico estadoFenologico) {
        // Agora passamos o ID da URL e o Objeto do Body para a Service
        return estadoFenologicoService.atualizarEstadoFenologico(id, estadoFenologico);
    }


    @DeleteMapping("/{id}/")
    public void deletarEstadoFenologico(@PathVariable Long id){
        estadoFenologicoService.deletarEstadoFenologico(id);
    }
    
}
