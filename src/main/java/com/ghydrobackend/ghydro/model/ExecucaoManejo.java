package com.ghydrobackend.ghydro.model;

import java.security.Timestamp;

import com.ghydrobackend.ghydro.model.enums.Origem;

public class ExecucaoManejo {
    private Long id;
    private Long plantioId;
    private Long recomendacaoId;
    private Timestamp inicio;
    private Timestamp fim;
    private Double volumeAguaAplicado;
    private Double energiaGasta;
    private Origem origem;
}
