package com.ghydrobackend.ghydro.repository;

import com.ghydrobackend.ghydro.model.DispositivoIrrigacao;
import com.ghydrobackend.ghydro.model.Proprietario;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DispositivoIrrigacaoRepository extends JpaRepository<DispositivoIrrigacao, Long>{

    boolean existsByNomeAndSetorId(String nome, Long setorId);

    boolean existsByNomeAndSetorIdAndIdNot(String nome, Long setorId, Long id);

    List<DispositivoIrrigacao> findAllBySetorPropriedadeProprietario(Proprietario proprietario);
    
}
