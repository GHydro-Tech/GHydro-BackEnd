package com.ghydrobackend.ghydro.model;


import java.security.Timestamp;
import java.util.List;

import com.ghydrobackend.ghydro.model.enums.StatusSensor;
import com.ghydrobackend.ghydro.model.enums.TipoSensor;

import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

public class Sensor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private List<TipoSensor> tipos;

    @Enumerated(EnumType.STRING)
    @Column
    private StatusSensor status;
    
    @Column(name ="dataInstalacao")
    private Timestamp dataInstalacao;

    @Column(name ="nivelBateria")
    private Double nivelBateria;

    @Column(name ="setorID")
    private Long setorID;

    private List<Long> leiturasIds;
}
