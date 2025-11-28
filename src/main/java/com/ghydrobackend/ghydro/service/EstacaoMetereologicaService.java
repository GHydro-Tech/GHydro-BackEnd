package com.ghydrobackend.ghydro.service;

import com.ghydrobackend.ghydro.model.EstacaoMetereologica;
import com.ghydrobackend.ghydro.repository.EstacaoMetereologicaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EstacaoMetereologicaService {

    @Autowired
    private EstacaoMetereologicaRepository estacaoMetereologicaRepository;

    public EstacaoMetereologica buscarPorId(Long id) {
        return estacaoMetereologicaRepository.findById(id).orElseThrow(() -> new RuntimeException("EstacaoMetereologica não encontrado com o ID: " + id));
    }

    public EstacaoMetereologica salvarEstacaoMetereologica(EstacaoMetereologica estacaoMetereologica) {
        return estacaoMetereologicaRepository.save(estacaoMetereologica);
    }

    public List<EstacaoMetereologica> listarEstacaoMetereologica() {
        return estacaoMetereologicaRepository.findAll();
    }

    public EstacaoMetereologica atualizarEstacaoMetereologica(EstacaoMetereologica estacaoMetereologica) {
        return estacaoMetereologicaRepository.save(estacaoMetereologica);
    }

    public void deletarEstacaoMetereologica(Long id) {
        estacaoMetereologicaRepository.deleteById(id);
    }
}
