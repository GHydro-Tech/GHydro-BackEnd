package com.ghydrobackend.ghydro.model;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

public class EstadoFenologico {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // ID da cultura associada ao estado fenológico
    private Long culturaId;

    @Column(name ="ordemSequencia")
    private Integer ordemSequencia;

    @Column(name ="nomeFase")
    private String nomeFase;

    @Column(name ="descricaoFase")
    private String descricaoFase;

    @Column(name ="descricaoFase")
    private Integer duracaoDias;

    @Column(name ="kCFase")
    private Double kCFase;

    @Column(name ="profundidadeRaiz_cm")
    private Double profundidadeRaiz_cm;
    
    @Column(name ="sensibilidadeKY")
    private Double sensibilidadeKY;
}
