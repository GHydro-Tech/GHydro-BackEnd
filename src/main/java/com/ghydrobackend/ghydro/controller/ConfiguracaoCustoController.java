package com.ghydrobackend.ghydro.controller;

import com.ghydrobackend.ghydro.model.ConfiguracaoCusto;
import com.ghydrobackend.ghydro.service.ConfiguracaoCustoService;

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
@RequestMapping("configuracaoCusto")
@CrossOrigin("*")
public class ConfiguracaoCustoController {

    @Autowired
    private ConfiguracaoCustoService configuracaoCustoService;

    @PostMapping
    public ConfiguracaoCusto salvarConfiguracaoCusto(@RequestBody ConfiguracaoCusto configuracaoCusto){
        return configuracaoCustoService.salvarConfiguracaoCusto(configuracaoCusto);
    }

    @GetMapping
    public List<ConfiguracaoCusto> listarConfiguracaoCusto(){
        return configuracaoCustoService.listarConfiguracaoCusto();
    }

    @PutMapping
    public ConfiguracaoCustoService atualizarConfiguracaoCustoService(@RequestBody ConfiguracaoCustoService configuracaoCustoService){
        return configuracaoCustoService.atualizarConfiguracaoCusto(configuracaoCusto);
    }

    @DeleteMapping("/{id}/")
    public void deletarConfiguracaoCusto(@PathVariable Long id){
        configuracaoCustoService.deletarConfiguracaoCusto(id);
    }
    
}
