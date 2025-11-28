package com.ghydrobackend.ghydro.controller;

import com.ghydrobackend.ghydro.model.DispositivoIrrigacao;
import com.ghydrobackend.ghydro.service.DispositivoIrrigacaoService;

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
@RequestMapping("dispositivoIrrigacao")
@CrossOrigin("*")
public class DispositivoIrrigacaoController {

    @Autowired
    private DispositivoIrrigacaoService dispositivoIrrigacaoService;

    @PostMapping
    public DispositivoIrrigacao salvarDispositivoIrrigacao(@RequestBody DispositivoIrrigacao dispositivoIrrigacao){
        return dispositivoIrrigacaoService.salvarDispositivoIrrigacao(dispositivoIrrigacao);
    }

    @GetMapping
    public List<DispositivoIrrigacao> listarDispositivoIrrigacao(){
        return dispositivoIrrigacaoService.listarDispositivoIrrigacao();
    }

    @PutMapping
    public DispositivoIrrigacao atualizarDispositivoIrrigacao(@RequestBody DispositivoIrrigacao dispositivoIrrigacao){
        return dispositivoIrrigacaoService.atualizarDispositivoIrrigacao(dispositivoIrrigacao);
    }

    @DeleteMapping("/{id}/")
    public void deletarDispositivoIrrigacao(@PathVariable Long id){
        dispositivoIrrigacaoService.deletarDispositivoIrrigacao(id);
    }
    
}
