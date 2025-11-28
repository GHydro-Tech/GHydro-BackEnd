package com.ghydrobackend.ghydro.service;

import com.ghydrobackend.ghydro.model.EstadoFenologico;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EstadoFenologicoService {

    @Autowired
    private EstadoFenologicoRepository estadoFenologicoRepository;

    public EstadoFenologico buscarPorId(Long id) {
        return estadoFenologicoRepository.findById(id).orElseThrow(() -> new RuntimeException("Estado Fenologico não encontrado com o ID: " + id));
    }

    public EstadoFenologico salvarEstadoFenologico(EstadoFenologico estadoFenologico) {
        return estadoFenologicoRepository.save(estadoFenologico);
    }

    public List<EstadoFenologico> listarEstadoFenologico() {
        return estadoFenologicoRepository.findAll();
    }

    public EstadoFenologico atualizarEstadoFenologico(EstadoFenologico estadoFenologico) {
        return estadoFenologicoRepository.save(estadoFenologico);
    }

    public void deletarEstadoFenologico(Long id) {
        estadoFenologicoRepository.deleteById(id);
    }
}
