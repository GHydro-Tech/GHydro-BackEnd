package com.ghydrobackend.ghydro.repository;

import com.ghydrobackend.ghydro.model.Sensor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SensorRepository extends JpaRepository<Sensor, Long>{
    
}
