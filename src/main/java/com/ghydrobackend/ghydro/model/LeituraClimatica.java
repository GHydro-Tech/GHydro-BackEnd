package com.ghydrobackend.ghydro.model;

import java.security.Timestamp;

public class LeituraClimatica {
    private Long id;
    private Long estacaoId;
    private Timestamp dataHora;
    private Double temperaturaMaxima;
    private Double temperaturaMinima;
    private Double umidadeRelativaAr;
    private Double velocidadeVento;
    private Double radiacaoSolar;
    private Double precipitacao;
    private Double etoCalculado;
}
