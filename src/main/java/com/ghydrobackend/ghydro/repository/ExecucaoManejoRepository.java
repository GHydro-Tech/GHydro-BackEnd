package com.ghydrobackend.ghydro.repository;

import com.ghydrobackend.ghydro.model.ExecucaoManejo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExecucaoManejoRepository extends JpaRepository<ExecucaoManejo, Long>{
    
}
