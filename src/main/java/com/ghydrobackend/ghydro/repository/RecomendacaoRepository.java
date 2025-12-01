package com.ghydrobackend.ghydro.repository;

import com.ghydrobackend.ghydro.model.Recomendacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecomendacaoRepository extends JpaRepository<Recomendacao, Long>{
    
}
