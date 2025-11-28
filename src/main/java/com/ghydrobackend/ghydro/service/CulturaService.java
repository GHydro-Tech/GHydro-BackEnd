package com.ghydrobackend.ghydro.service;

import com.ghydrobackend.ghydro.model.Cultura;
import com.ghydrobackend.ghydro.repository.CulturaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CulturaService {

    @Autowired
    private CulturaRepository culturaRepository;

    public Cultura buscarPorId(Long id) {
        return culturaRepository.findById(id).orElseThrow(() -> new RuntimeException("Cultura não encontrado com o ID: " + id));
    }

    public Cultura salvarCultura(Cultura cultura) {
        return culturaRepository.save(cultura);
    }

    public List<Cultura> listarCultura() {
        return culturaRepository.findAll();
    }

    public Cultura atualizarCultura(Cultura cultura) {
        return culturaRepository.save(cultura);
    }

    public void deletarCultura(Long id) {
        culturaRepository.deleteById(id);
    }
}
