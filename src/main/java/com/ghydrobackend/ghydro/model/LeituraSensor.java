package com.ghydrobackend.ghydro.model;

import java.security.Timestamp;

import com.ghydrobackend.ghydro.model.enums.UnidadeMedida;

public class LeituraSensor {
    private String id;
    private String sensorId;
    private Timestamp timestamp;
    private double valorBruto;
    private double valorTratado;
    private UnidadeMedida unidadeMedida;
}
