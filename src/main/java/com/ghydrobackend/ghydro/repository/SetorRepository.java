package com.ghydrobackend.ghydro.repository;

import com.ghydrobackend.ghydro.model.Proprietario;
import com.ghydrobackend.ghydro.model.Setor;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SetorRepository extends JpaRepository<Setor, Long>{

    boolean existsByNomeAndPropriedadeId(String nome, Long propriedadeId);

    boolean existsByNomeAndPropriedadeIdAndIdNot(String nome, Long propriedadeId, Long id);

    // Setor -> Propriedade -> Proprietario
    List<Setor> findAllByPropriedadeProprietario(Proprietario proprietario);
}
