package com.ghydrobackend.ghydro.model;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

public class TipoSolo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name ="descricao")
    private String descricao;

    @Column(name ="capacidadeCampo")
    private Double capacidadeCampo;

    @Column(name ="pontoMurcha")
    private Double pontoMurcha;

    @Column(name ="densidadeAparente")
    private Double densidadeAparente;

    @Column(name ="taxaInfiltracaoBasica")
    private Double taxaInfiltracaoBasica;

    // n:n
    // revisar isso
    private List<Long> SetoresIds;
}
