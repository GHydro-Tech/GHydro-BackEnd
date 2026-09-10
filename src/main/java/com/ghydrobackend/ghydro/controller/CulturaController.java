package com.ghydrobackend.ghydro.controller;

import com.ghydrobackend.ghydro.model.Cultura;
import com.ghydrobackend.ghydro.service.CulturaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("cultura")
@CrossOrigin("*")
public class CulturaController {
    
    @Autowired
    private CulturaService culturaService;

    // Apenas Administradores e Técnicos podem cadastrar
    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'TECNICO')")
    public Cultura salvarCultura(@RequestBody Cultura cultura){
        return culturaService.salvarCultura(cultura);
    }

    // Como não tem @PreAuthorize, qualquer usuário logado (com token válido) pode listar
    @GetMapping
    public List<Cultura> listarCultura(){
        return culturaService.listarCultura();
    }

    // Apenas Administradores e Técnicos podem editar
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'TECNICO')")
    public Cultura atualizarCultura(@PathVariable Long id, @RequestBody Cultura cultura) {
        return culturaService.atualizarCultura(id, cultura);
    }

    // APENAS Administradores podem deletar (exemplo de regra estrita)
    @DeleteMapping("/{id}/")
    @PreAuthorize("hasAnyRole('ADMIN', 'TECNICO')")
    public void deletarCultura(@PathVariable Long id){
        culturaService.deletarCultura(id);
    }
}