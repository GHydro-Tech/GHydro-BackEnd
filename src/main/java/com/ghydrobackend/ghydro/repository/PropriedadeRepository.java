package com.ghydrobackend.ghydro.repository;

import com.ghydrobackend.ghydro.model.Propriedade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PropriedadeRepository extends JpaRepository<Propriedade, Long>{

    boolean existsByNomeAndProprietarioId(String nome, Long proprietarioId);

    boolean existsByNomeAndProprietarioIdAndIdNot(String nome, Long proprietarioId, Long id);
    
}
