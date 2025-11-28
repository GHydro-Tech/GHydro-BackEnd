package com.ghydrobackend.ghydro.service;


import com.ghydrobackend.ghydro.model.Setor;
import com.ghydrobackend.ghydro.repository.SetorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SetorService {

    @Autowired
    private SetorRepository setorRepository;

    public Setor buscarPorId(Long id) {
        return setorRepository.findById(id).orElseThrow(() -> new RuntimeException("Setor não encontrado com o ID: " + id));
    }

    public Setor salvarSetor(Setor setor) {
        return setorRepository.save(setor);
    }

    public List<Setor> listarSetor() {
        return setorRepository.findAll();
    }

    public Setor atualizarSetor(Setor setor) {
        return setorRepository.save(setor);
    }

    public void deletarSetor(Long id) {
        setorRepository.deleteById(id);
    }
}
