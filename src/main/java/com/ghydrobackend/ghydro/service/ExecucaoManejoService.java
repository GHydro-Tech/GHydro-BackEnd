package com.ghydrobackend.ghydro.service;

import com.ghydrobackend.ghydro.model.ExecucaoManejo;
import com.ghydrobackend.ghydro.repository.ExecucaoManejoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExecucaoManejoService {

    @Autowired
    private ExecucaoManejoRepository execucaoManejoRepository;

    public ExecucaoManejo buscarPorId(Long id) {
        return execucaoManejoRepository.findById(id).orElseThrow(() -> new RuntimeException("ExecucaoManejo não encontrado com o ID: " + id));
    }

    public ExecucaoManejo salvarExecucaoManejo(ExecucaoManejo execucaoManejo) {
        return execucaoManejoRepository.save(execucaoManejo);
    }

    public List<ExecucaoManejo> listarExecucaoManejo() {
        return execucaoManejoRepository.findAll();
    }

    public ExecucaoManejo atualizarExecucaoManejo(ExecucaoManejo execucaoManejo) {
        return execucaoManejoRepository.save(execucaoManejo);
    }

    public void deletarExecucaoManejo(Long id) {
        execucaoManejoRepository.deleteById(id);
    }
}
