package com.ghydrobackend.ghydro.controller;

import com.ghydrobackend.ghydro.model.Sensor;
import com.ghydrobackend.ghydro.service.SensorService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("sensor")
@CrossOrigin("*")
public class SensorController {

    @Autowired
    private SensorService sensorService;

    @PostMapping
    public Sensor salvarSensor(@RequestBody Sensor sensor){
        return sensorService.salvarSensor(sensor);
    }

    @GetMapping
    public List<Sensor> listarSensor(){
        return sensorService.listarSensor();
    }

    @PutMapping
    public Sensor atualizarSensor(@RequestBody Sensor sensor){
        return sensorService.atualizarSensor(sensor);
    }

    @DeleteMapping("/{id}/")
    public void deletarSensor(@PathVariable Long id){
        sensorService.deletarSensor(id);
    }
    
}
