package com.ghydrobackend.ghydro.model;

import java.util.List;

import com.ghydrobackend.ghydro.model.enums.StatusPlantio;

import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

public class Plantio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long culturaId;

    @Column(name ="dataPlantio")
    private String dataPlantio;
    
    @Column(name ="dataColheitaEstimada")
    private String dataColheitaEstimada;

    @Enumerated(EnumType.STRING)
    @Column
    private StatusPlantio statusPlantio;

    private List<Long> manejosIds;
    private List<Long> setoresIds;
}
