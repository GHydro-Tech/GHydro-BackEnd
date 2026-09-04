package com.ghydrobackend.ghydro.service;

import com.ghydrobackend.ghydro.model.Cultura;
import com.ghydrobackend.ghydro.model.EstadoFenologico;
import com.ghydrobackend.ghydro.repository.CulturaRepository;
import com.ghydrobackend.ghydro.repository.EstadoFenologicoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class EstadoFenologicoService {

    @Autowired
    private EstadoFenologicoRepository estadoRepository;

    @Autowired
    private CulturaRepository culturaRepository; // Para validar a cultura

    public List<EstadoFenologico> listarEstadoFenologico() {
        return estadoRepository.findAll();
    }

    public EstadoFenologico buscarPorId(Long id) {
        return estadoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Estado Fenológico não encontrado com o ID: " + id));
    }

    @Transactional
    public EstadoFenologico salvarEstadoFenologico(EstadoFenologico estado) {
        validarCamposObrigatorios(estado);
        validarValoresAgronomicos(estado);

        // 1. Carrega a Cultura completa
        carregarCultura(estado);

        // 2. Valida duplicidade de Ordem e Nome NA MESMA CULTURA
        validarDuplicidade(estado, null);

        return estadoRepository.save(estado);
    }

    @Transactional
    public EstadoFenologico atualizarEstadoFenologico(Long id, EstadoFenologico estadoAtualizado) {
        EstadoFenologico existente = buscarPorId(id);

        validarCamposObrigatorios(estadoAtualizado);
        validarValoresAgronomicos(estadoAtualizado);

        // Se mudou a cultura (raro, mas possível)
        if (estadoAtualizado.getCultura() != null) {
            carregarCultura(estadoAtualizado);
            existente.setCultura(estadoAtualizado.getCultura());
        }

        // Atualiza dados
        existente.setOrdemSequencia(estadoAtualizado.getOrdemSequencia());
        existente.setNomeFase(estadoAtualizado.getNomeFase());
        existente.setDescricaoFase(estadoAtualizado.getDescricaoFase());
        existente.setDuracaoDias(estadoAtualizado.getDuracaoDias());
        existente.setKCFase(estadoAtualizado.getKCFase());
        existente.setProfundidadeRaiz_cm(estadoAtualizado.getProfundidadeRaiz_cm());
        existente.setSensibilidadeKY(estadoAtualizado.getSensibilidadeKY());

        // Valida duplicidade ignorando o próprio ID
        validarDuplicidade(existente, id);

        return estadoRepository.save(existente);
    }

    public void deletarEstadoFenologico(Long id) {
        if (!estadoRepository.existsById(id)) {
            throw new EntityNotFoundException("Estado Fenológico não encontrado para exclusão.");
        }
        estadoRepository.deleteById(id);
    }

    // --- Métodos Auxiliares ---

    private void validarCamposObrigatorios(EstadoFenologico e) {
        if (e.getNomeFase() == null || e.getNomeFase().trim().isEmpty()) {
            throw new IllegalArgumentException("O nome da fase é obrigatório.");
        }
        if (e.getOrdemSequencia() == null) {
            throw new IllegalArgumentException("A ordem da sequência é obrigatória.");
        }
        if (e.getDuracaoDias() == null) {
            throw new IllegalArgumentException("A duração em dias é obrigatória.");
        }
    }

    private void validarValoresAgronomicos(EstadoFenologico e) {
        if (e.getOrdemSequencia() <= 0) {
            throw new IllegalArgumentException("A ordem da sequência deve ser maior que zero.");
        }
        if (e.getDuracaoDias() <= 0) {
            throw new IllegalArgumentException("A duração em dias deve ser maior que zero.");
        }
        if (e.getKCFase() != null && e.getKCFase() <= 0) {
            throw new IllegalArgumentException("O coeficiente Kc deve ser maior que zero.");
        }
        if (e.getProfundidadeRaiz_cm() != null && e.getProfundidadeRaiz_cm() <= 0) {
            throw new IllegalArgumentException("A profundidade da raiz deve ser maior que zero.");
        }
    }

    private void carregarCultura(EstadoFenologico e) {
        if (e.getCultura() == null || e.getCultura().getId() == null) {
            throw new IllegalArgumentException("É obrigatório informar a Cultura.");
        }
        Cultura cultura = culturaRepository.findById(e.getCultura().getId())
                .orElseThrow(() -> new EntityNotFoundException("A Cultura informada não existe."));
        e.setCultura(cultura);
    }

    private void validarDuplicidade(EstadoFenologico e, Long idAtualizacao) {
        Long culturaId = e.getCultura().getId();

        // Se for cadastro novo (idAtualizacao == null)
        if (idAtualizacao == null) {
            if (estadoRepository.existsByOrdemSequenciaAndCulturaId(e.getOrdemSequencia(), culturaId)) {
                throw new IllegalArgumentException("Já existe uma fase com a ordem " + e.getOrdemSequencia() + " para esta cultura.");
            }
            if (estadoRepository.existsByNomeFaseAndCulturaId(e.getNomeFase(), culturaId)) {
                throw new IllegalArgumentException("Já existe uma fase chamada '" + e.getNomeFase() + "' para esta cultura.");
            }
        }
        // Se for atualização
        else {
            if (estadoRepository.existsByOrdemSequenciaAndCulturaIdAndIdNot(e.getOrdemSequencia(), culturaId, idAtualizacao)) {
                throw new IllegalArgumentException("Já existe outra fase com a ordem " + e.getOrdemSequencia() + " para esta cultura.");
            }
            if (estadoRepository.existsByNomeFaseAndCulturaIdAndIdNot(e.getNomeFase(), culturaId, idAtualizacao)) {
                throw new IllegalArgumentException("Já existe outra fase chamada '" + e.getNomeFase() + "' para esta cultura.");
            }
        }
    }
}