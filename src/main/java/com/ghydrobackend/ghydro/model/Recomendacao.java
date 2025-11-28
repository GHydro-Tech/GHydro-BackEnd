package com.ghydrobackend.ghydro.model;

import java.security.Timestamp;

import com.ghydrobackend.ghydro.model.enums.StatusRecomendacao;
import com.ghydrobackend.ghydro.model.enums.TipoAcao;

public class Recomendacao {
    private Long id;
    private String plantioId;
    private Timestamp dataGeracao;
    private TipoAcao tipoAcao;
    private Float quantidade;
    // Ex: "2 horas", "30 minutos" não sei se é string
    private String duracao;
    private String observacoes;
    private String agenteResponsavel;
    private StatusRecomendacao status;
    private Timestamp dataConclusao;
    // Ele gera uma execução de manejo associada, mas pode gerar ou não, o que faz esse atributo não existir sempre, então pode ser nulo
    private Long execucaoManejoId;
}
