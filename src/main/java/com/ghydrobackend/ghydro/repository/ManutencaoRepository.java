package com.ghydrobackend.ghydro.repository;

import com.ghydrobackend.ghydro.model.Manutencao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ManutencaoRepository extends JpaRepository<Manutencao, Long> {
    
    // Filtra o "prontuário médico" de um equipamento específico
    List<Manutencao> findByEquipamentoIdAndTipoEquipamento(Long equipamentoId, String tipoEquipamento);
}