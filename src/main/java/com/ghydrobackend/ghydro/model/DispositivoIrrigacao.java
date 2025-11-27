package com.ghydrobackend.ghydro.model;

import com.ghydrobackend.ghydro.model.enums.TipoDispositivo;

public class DispositivoIrrigacao {
    private String id;
    private String nome;
    private TipoDispositivo tipoDispositivo;
    private Float eficienciaIrrigacao;
    private Float vazaoNominal;
    private Float potenciaMotor;
}
