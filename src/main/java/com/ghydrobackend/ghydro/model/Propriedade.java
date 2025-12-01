package com.ghydrobackend.ghydro.model;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

public class Propriedade {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name ="nome")
    private String nome;

    @Column(name ="localizacao")
    private String localizacao;

    private Long configuracaoCustoId;

    private Long proprietarioId;

    private List<Long> SetoresIds;
    // Relação com Estacao Metrologica
    // Cada propriedade pode ter várias estações metrológicas associadas
    // Mas essa relação precisa ser revisada posteriormente
    private List<Long> EstacoesIds;
}
