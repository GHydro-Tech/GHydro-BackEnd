package com.ghydrobackend.ghydro.service;

import com.ghydrobackend.ghydro.model.LeituraSensor;
import com.ghydrobackend.ghydro.repository.LeituraSensorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LeituraSensorService {

    @Autowired
    private LeituraSensorRepository leituraSensorRepository;

    public LeituraSensor buscarPorId(Long id) {
        return leituraSensorRepository.findById(id).orElseThrow(() -> new RuntimeException("LeituraSensor não encontrado com o ID: " + id));
    }

    public LeituraSensor salvarLeituraSensor(LeituraSensor leituraSensor) {
        return leituraSensorRepository.save(leituraSensor);
    }

    public List<LeituraSensor> listarLeituraSensor() {
        return leituraSensorRepository.findAll();
    }

    public LeituraSensor atualizarLeituraSensor(LeituraSensor leituraSensor) {
        return leituraSensorRepository.save(leituraSensor);
    }

    public void deletarLeituraSensor(Long id) {
        leituraSensorRepository.deleteById(id);
    }
}
