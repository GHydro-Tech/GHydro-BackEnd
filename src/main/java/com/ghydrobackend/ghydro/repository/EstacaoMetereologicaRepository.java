package com.ghydrobackend.ghydro.repository;

import com.ghydrobackend.ghydro.model.EstacaoMetereologica;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EstacaoMetereologicaRepository extends JpaRepository<EstacaoMetereologica, Long>{
    
}
