package com.ghydrobackend.ghydro.model;

import java.security.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

public class LeituraClimatica {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long estacaoId;

    @Column(name ="dataHora")
    private Timestamp dataHora;

    @Column(name ="temperaturaMaxima")
    private Double temperaturaMaxima;

    @Column(name ="temperaturaMinima")
    private Double temperaturaMinima;

    @Column(name ="umidadeRelativaAr")
    private Double umidadeRelativaAr;

    @Column(name ="velocidadeVento")
    private Double velocidadeVento;

    @Column(name ="radiacaoSolar")
    private Double radiacaoSolar;

    @Column(name ="precipitacao")
    private Double precipitacao;
    
    @Column(name ="etoCalculado")
    private Double etoCalculado;
}
