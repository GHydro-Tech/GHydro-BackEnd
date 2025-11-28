package com.ghydrobackend.ghydro.service;


import com.ghydrobackend.ghydro.model.TipoSolo;
import com.ghydrobackend.ghydro.repository.TipoSoloRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TipoSoloService {

    @Autowired
    private TipoSoloRepository tipoSoloRepository;

    public TipoSolo buscarPorId(Long id) {
        return tipoSoloRepository.findById(id).orElseThrow(() -> new RuntimeException("Tipo de Solo não encontrado com o ID: " + id));
    }

    public TipoSolo salvarTipoSolo(TipoSolo tipoSolo) {
        return tipoSoloRepository.save(tipoSolo);
    }

    public List<TipoSolo> listarTipoSolo() {
        return tipoSoloRepository.findAll();
    }

    public TipoSolo atualizarTipoSolo(TipoSolo tipoSolo) {
        return tipoSoloRepository.save(tipoSolo);
    }

    public void deletarTipoSolo(Long id) {
        tipoSoloRepository.deleteById(id);
    }
}
