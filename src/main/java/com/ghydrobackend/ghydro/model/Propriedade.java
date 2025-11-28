package com.ghydrobackend.ghydro.model;

import java.util.List;

public class Propriedade {
    private Long id;
    private String nome;
    private String localizacao;
    private Long configuracaoCustoId;
    private Long proprietarioId;
    private List<Long> SetoresIds;
    // Relação com Estacao Metrologica
    // Cada propriedade pode ter várias estações metrológicas associadas
    // Mas essa relação precisa ser revisada posteriormente
    private List<Long> EstacoesIds;
}
