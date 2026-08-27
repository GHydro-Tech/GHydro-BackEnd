package com.ghydrobackend.ghydro.repository;

import com.ghydrobackend.ghydro.model.Proprietario;
import com.ghydrobackend.ghydro.model.Sensor;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SensorRepository extends JpaRepository<Sensor, Long>{

    // Sensor -> Setor -> Propriedade -> Proprietario
    List<Sensor> findAllBySetorPropriedadeProprietario(Proprietario proprietario);
    
}
