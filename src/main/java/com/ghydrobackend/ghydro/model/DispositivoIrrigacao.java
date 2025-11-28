package com.ghydrobackend.ghydro.model;

import com.ghydrobackend.ghydro.model.enums.TipoDispositivo;

public class DispositivoIrrigacao {
    private Double id;
    private String nome;
    private TipoDispositivo tipoDispositivo;
    private Float eficienciaIrrigacao;
    private Float vazaoNominal;
    private Float potenciaMotor;
    // Relacionamento com Setor
    private Long setorId;
}
