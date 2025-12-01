package com.ghydrobackend.ghydro.repository;

import com.ghydrobackend.ghydro.model.Setor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SetorRepository extends JpaRepository<Setor, Long>{
    
}
