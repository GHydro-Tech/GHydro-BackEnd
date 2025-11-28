package com.ghydrobackend.ghydro.model;

import com.ghydrobackend.ghydro.model.enums.TipoDispositivo;

import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

public class DispositivoIrrigacao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name ="nome")
    private String nome;

    @Enumerated(EnumType.STRING)
    @Column
    private TipoDispositivo tipoDispositivo;

    @Column(name ="eficienciaIrrigacao")
    private Double eficienciaIrrigacao;

    @Column(name ="vazaoNominal")
    private Double vazaoNominal;

    @Column(name ="potenciaMotor")
    private Double potenciaMotor;
    
    // Relacionamento com Setor
    private Long setorId;
}
