package com.ghydrobackend.ghydro.service;


import com.ghydrobackend.ghydro.model.Proprietario;
import com.ghydrobackend.ghydro.repository.ProprietarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProprietarioService {

    @Autowired
    private ProprietarioRepository proprietarioRepository;

    public Proprietario buscarPorId(Long id) {
        return proprietarioRepository.findById(id).orElseThrow(() -> new RuntimeException("Proprietário não encontrado com o ID: " + id));
    }

    public Proprietario salvarProprietario(Proprietario proprietario) {
        return proprietarioRepository.save(proprietario);
    }

    public List<Proprietario> listarProprietario() {
        return proprietarioRepository.findAll();
    }

    public Proprietario atualizarProprietario(Proprietario proprietario) {
        return proprietarioRepository.save(proprietario);
    }

    public void deletarProprietario(Long id) {
        proprietarioRepository.deleteById(id);
    }
}
