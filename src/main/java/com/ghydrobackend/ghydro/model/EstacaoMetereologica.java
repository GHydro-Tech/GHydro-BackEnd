package com.ghydrobackend.ghydro.model;

import java.util.List;

import com.ghydrobackend.ghydro.model.enums.TipoEstacao;

import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

public class EstacaoMetereologica {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name ="nome")
    private String nome;

    @Enumerated(EnumType.STRING)
    @Column
    private TipoEstacao tipo;

    @Column(name ="apiSource")
    private String apiSource;

    @Column(name ="latitude")
    private String latitude;

    @Column(name ="longitude")
    private String longitude;

    @Column(name ="apiKey")
    private String apiKey;

    //Representa as leituras climaticas coletadas pela estacao
    private List<Long> leiturasClimaticas;

    //Representa as propriedades as quais a estacao esta associada
    //Relacionamento precisa ser revisto
    //Não faz sentido uma estacao ter varias propriedades
    private List<Long> propriedades;
}
