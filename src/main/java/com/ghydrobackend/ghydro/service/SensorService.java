package com.ghydrobackend.ghydro.service;

import com.ghydrobackend.ghydro.model.Sensor;
import com.ghydrobackend.ghydro.model.Setor;
import com.ghydrobackend.ghydro.repository.SensorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SensorService {

    @Autowired
    private SensorRepository sensorRepository;

    public Sensor buscarPorId(Long id) {
        return sensorRepository.findById(id).orElseThrow(() -> new RuntimeException("Sensor não encontrado com o ID: " + id));
    }

    public Sensor salvarSensor(Sensor sensor) {
        return sensorRepository.save(sensor);
    }

    public List<Sensor> listarSensor() {
        return sensorRepository.findAll();
    }

    public Sensor atualizarSensor(Sensor sensor) {
        return sensorRepository.save(sensor);
    }

    public void deletarSensor(Long id) {
        sensorRepository.deleteById(id);
    }
}
