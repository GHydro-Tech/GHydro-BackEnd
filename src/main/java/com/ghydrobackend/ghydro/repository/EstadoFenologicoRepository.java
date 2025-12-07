package com.ghydrobackend.ghydro.repository;

import com.ghydrobackend.ghydro.model.EstadoFenologico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EstadoFenologicoRepository extends JpaRepository<EstadoFenologico, Long>{

    boolean existsByOrdemSequenciaAndCulturaId(Integer ordem, Long culturaId);

    boolean existsByNomeFaseAndCulturaId(String nome, Long culturaId);

    boolean existsByOrdemSequenciaAndCulturaIdAndIdNot(Integer ordem, Long culturaId, Long id);

    boolean existsByNomeFaseAndCulturaIdAndIdNot(String nome, Long culturaId, Long id);
    
}
