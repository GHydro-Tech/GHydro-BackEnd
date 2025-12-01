package com.ghydrobackend.ghydro.repository;

import com.ghydrobackend.ghydro.model.Sensor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SensorRepository extends JpaRepository<Sensor, Long>{
    
}
