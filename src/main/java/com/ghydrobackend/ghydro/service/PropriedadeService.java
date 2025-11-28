package com.ghydrobackend.ghydro.service;

import com.ghydrobackend.ghydro.model.Propriedade;
import com.ghydrobackend.ghydro.repository.PropriedadeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PropriedadeService {

    @Autowired
    private PropriedadeRepository propriedadeRepository;

    public Propriedade buscarPorId(Long id) {
        return propriedadeRepository.findById(id).orElseThrow(() -> new RuntimeException("Propriedade não encontrado com o ID: " + id));
    }

    public Propriedade salvarPropriedade(Propriedade propriedade) {
        return propriedadeRepository.save(propriedade);
    }

    public List<Propriedade> listarPropriedade() {
        return propriedadeRepository.findAll();
    }

    public Propriedade atualizarPropriedade(Propriedade propriedade) {
        return propriedadeRepository.save(propriedade);
    }

    public void deletarPropriedade(Long id) {
        propriedadeRepository.deleteById(id);
    }
}
