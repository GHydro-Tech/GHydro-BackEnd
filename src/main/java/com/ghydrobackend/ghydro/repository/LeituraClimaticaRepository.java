package com.ghydrobackend.ghydro.repository;

import com.ghydrobackend.ghydro.model.LeituraClimatica;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LeituraClimaticaRepository extends JpaRepository<LeituraClimatica, Long>{
    
}
