package com.ghydrobackend.ghydro.model;

public class EstadoFenologico {
    private Long id;
    // ID da cultura associada ao estado fenológico
    private Long culturaId;
    private Integer ordemSequencia;
    private String nomeFase;
    private String descricaoFase;
    private Integer duracaoDias;
    private Double kCFase;
    private Double profundidadeRaiz_cm;
    private Double sensibilidadeKY;
}
