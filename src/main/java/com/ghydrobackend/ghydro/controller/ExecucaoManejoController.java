package com.ghydrobackend.ghydro.controller;

import com.ghydrobackend.ghydro.model.ExecucaoManejo;
import com.ghydrobackend.ghydro.service.ExecucaoManejoService;

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
@RequestMapping("execucaoManejo")
@CrossOrigin("*")
public class ExecucaoManejoController {

    @Autowired
    private ExecucaoManejoService execucaoManejoService;

    @PostMapping
    public ExecucaoManejo salvarExecucaoManejo(@RequestBody ExecucaoManejo execucaoManejo){
        return execucaoManejoService.salvarExecucaoManejo(execucaoManejo);
    }

    @GetMapping
    public List<ExecucaoManejo> listarExecucaoManejo(){
        return execucaoManejoService.listarExecucaoManejo();
    }

    @PutMapping("/{id}")
    public ExecucaoManejo atualizarExecucaoManejo(@PathVariable Long id, @RequestBody ExecucaoManejo execucaoManejo) {
        // Agora passamos o ID da URL e o Objeto do Body para a Service
        return execucaoManejoService.atualizarExecucaoManejo(id, execucaoManejo);
    }

    @DeleteMapping("/{id}/")
    public void deletarExecucaoManejo(@PathVariable Long id){
        execucaoManejoService.deletarExecucaoManejo(id);
    }
    
}
