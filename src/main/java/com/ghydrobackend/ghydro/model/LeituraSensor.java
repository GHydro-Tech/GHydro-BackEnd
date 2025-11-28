package com.ghydrobackend.ghydro.model;

import java.security.Timestamp;

import com.ghydrobackend.ghydro.model.enums.UnidadeMedida;

import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

public class LeituraSensor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long sensorId;

    @Column(name ="timestamp")
    private Timestamp timestamp;

    @Column(name ="valorBruto")
    private double valorBruto;

    @Column(name ="valorTratado")
    private double valorTratado;
    
    @Enumerated(EnumType.STRING)
    @Column
    private UnidadeMedida unidadeMedida;
}
