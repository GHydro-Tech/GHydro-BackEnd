package com.ghydrobackend.ghydro.controller;

import com.ghydrobackend.ghydro.model.ConfiguracaoCusto;
import com.ghydrobackend.ghydro.service.ConfiguracaoCustoService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
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
@RequestMapping("configuracaoCusto")
@CrossOrigin("*")
public class ConfiguracaoCustoController {

    @Autowired
    private ConfiguracaoCustoService configuracaoCustoService;

    @PostMapping
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_TECNICO')")
    public ConfiguracaoCusto salvarConfiguracaoCusto(@RequestBody ConfiguracaoCusto configuracaoCusto){
        return configuracaoCustoService.salvarConfiguracaoCusto(configuracaoCusto);
    }

    @GetMapping
    public List<ConfiguracaoCusto> listarConfiguracaoCusto(){
        return configuracaoCustoService.listarConfiguracaoCusto();
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_TECNICO')")
    public ConfiguracaoCusto atualizarConfiguracaoCusto(@PathVariable Long id, @RequestBody ConfiguracaoCusto configuracaoCusto) {
        return configuracaoCustoService.atualizarConfiguracaoCusto(id, configuracaoCusto);
    }

    @DeleteMapping("/{id}/")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_TECNICO')")
    public void deletarConfiguracaoCusto(@PathVariable Long id){
        configuracaoCustoService.deletarConfiguracaoCusto(id);
    }
    
}
