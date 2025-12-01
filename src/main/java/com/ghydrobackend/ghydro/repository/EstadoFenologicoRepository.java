package com.ghydrobackend.ghydro.repository;

import com.ghydrobackend.ghydro.model.EstadoFenologico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EstadoFenologicoRepository extends JpaRepository<EstadoFenologico, Long>{
    
}
