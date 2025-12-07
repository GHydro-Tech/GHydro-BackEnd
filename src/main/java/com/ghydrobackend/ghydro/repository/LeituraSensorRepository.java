package com.ghydrobackend.ghydro.repository;

import com.ghydrobackend.ghydro.model.LeituraSensor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface LeituraSensorRepository extends JpaRepository<LeituraSensor, Long>{

    boolean existsBySensorIdAndTimestamp(Long sensorId, LocalDateTime timestamp);
    
}
