package com.ghydrobackend.ghydro.repository;

import com.ghydrobackend.ghydro.model.LeituraSensor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LeituraSensorRepository extends JpaRepository<LeituraSensor, Long>{
    
}
