package com.ghydrobackend.ghydro.controller;

import com.ghydrobackend.ghydro.model.LeituraSensor;
import com.ghydrobackend.ghydro.service.LeituraSensorService;

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
@RequestMapping("leituraSensor")
@CrossOrigin("*")
public class LeituraSensorController {

    @Autowired
    private LeituraSensorService leituraSensorService;

    @PostMapping
    public LeituraSensor salvarLeituraSensor(@RequestBody LeituraSensor leituraSensor){
        return leituraSensorService.salvarLeituraSensor(leituraSensor);
    }

    @GetMapping
    public List<LeituraSensor> listarLeituraSensor(){
        return leituraSensorService.listarLeituraSensor();
    }

    @PutMapping
    public LeituraSensor atualizarLeituraSensor(@RequestBody LeituraSensor leituraSensor){
        return leituraSensorService.atualizarLeituraSensor(leituraSensor);
    }

    @DeleteMapping("/{id}/")
    public void deletarLeituraSensor(@PathVariable Long id){
        leituraSensorService.deletarLeituraSensor(id);
    }
    
}
