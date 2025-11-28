package com.ghydrobackend.ghydro.service;

import com.ghydrobackend.ghydro.model.LeituraClimatica;
import com.ghydrobackend.ghydro.repository.LeituraClimaticaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LeituraClimaticaService {

    @Autowired
    private LeituraClimaticaRepository leituraClimaticaRepository;

    public LeituraClimatica buscarPorId(Long id) {
        return leituraClimaticaRepository.findById(id).orElseThrow(() -> new RuntimeException("LeituraClimatica não encontrado com o ID: " + id));
    }

    public LeituraClimatica salvarLeituraClimatica(LeituraClimatica leituraClimatica) {
        return leituraClimaticaRepository.save(leituraClimatica);
    }

    public List<LeituraClimatica> listarLeituraClimatica() {
        return leituraClimaticaRepository.findAll();
    }

    public LeituraClimatica atualizarLeituraClimatica(LeituraClimatica leituraClimatica) {
        return leituraClimaticaRepository.save(leituraClimatica);
    }

    public void deletarLeituraClimatica(Long id) {
        leituraClimaticaRepository.deleteById(id);
    }
}
