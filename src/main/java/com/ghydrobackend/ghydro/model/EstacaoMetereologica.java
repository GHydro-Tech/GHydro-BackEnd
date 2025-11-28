package com.ghydrobackend.ghydro.model;

import java.util.List;

import com.ghydrobackend.ghydro.model.enums.TipoEstacao;

public class EstacaoMetereologica {
    private Long id;
    private String nome;
    private TipoEstacao tipo;
    private String apiSource;
    private String latitude;
    private String longitude;
    private String apiKey;
    //Representa as leituras climaticas coletadas pela estacao
    private List<Long> leiturasClimaticas;
    //Representa as propriedades as quais a estacao esta associada
    //Relacionamento precisa ser revisto
    //Não faz sentido uma estacao ter varias propriedades
    private List<Long> propriedades;
}
