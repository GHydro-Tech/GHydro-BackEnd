package com.ghydrobackend.ghydro.controller;

import com.ghydrobackend.ghydro.model.Recomendacao;
import com.ghydrobackend.ghydro.service.RecomendacaoService;

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
@RequestMapping("recomendacao")
@CrossOrigin("*")
public class RecomendacaoController {

    @Autowired
    private RecomendacaoService recomendacaoService;

    @PostMapping
    public Recomendacao salvarRecomendacao(@RequestBody Recomendacao recomendacao){
        return recomendacaoService.salvarRecomendacao(recomendacao);
    }

    @GetMapping
    public List<Recomendacao> listarRecomendacao(){
        return recomendacaoService.listarRecomendacao();
    }

    @PutMapping
    public Recomendacao atualizarRecomendacao(@RequestBody Recomendacao recomendacao){
        return recomendacaoService.atualizarRecomendacao(recomendacao);
    }

    @DeleteMapping("/{id}/")
    public void deletarRecomendacao(@PathVariable Long id){
        recomendacaoService.deletarRecomendacao(id);
    }
    
}
