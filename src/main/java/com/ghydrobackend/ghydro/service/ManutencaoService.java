package com.ghydrobackend.ghydro.service;

import com.ghydrobackend.ghydro.model.Manutencao;
import com.ghydrobackend.ghydro.repository.ManutencaoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ManutencaoService {

    @Autowired
    private ManutencaoRepository repository;

    public Manutencao salvarManutencao(Manutencao manutencao) {
        return repository.save(manutencao);
    }

    public List<Manutencao> listarManutencoes() {
        return repository.findAll();
    }

    public List<Manutencao> buscarPorEquipamento(Long equipamentoId, String tipoEquipamento) {
        return repository.findByEquipamentoIdAndTipoEquipamento(equipamentoId, tipoEquipamento);
    }

    public Manutencao buscarPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Registro de manutenção não encontrado."));
    }

    public Manutencao atualizarManutencao(Long id, Manutencao dadosAtualizados) {
        Manutencao manutencao = buscarPorId(id);
        
        manutencao.setEquipamentoId(dadosAtualizados.getEquipamentoId());
        manutencao.setTipoEquipamento(dadosAtualizados.getTipoEquipamento());
        manutencao.setTipoManutencao(dadosAtualizados.getTipoManutencao());
        manutencao.setDataManutencao(dadosAtualizados.getDataManutencao());
        manutencao.setDescricaoProblema(dadosAtualizados.getDescricaoProblema());
        manutencao.setSolucaoAplicada(dadosAtualizados.getSolucaoAplicada());
        manutencao.setTecnicoResponsavel(dadosAtualizados.getTecnicoResponsavel());

        return repository.save(manutencao);
    }

    public void deletarManutencao(Long id) {
        Manutencao manutencao = buscarPorId(id);
        repository.delete(manutencao);
    }
}