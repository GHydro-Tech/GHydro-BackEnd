package com.ghydrobackend.ghydro.repository;

import com.ghydrobackend.ghydro.model.TipoSolo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TipoSoloRepository extends JpaRepository<TipoSolo, Long>{
    
}
