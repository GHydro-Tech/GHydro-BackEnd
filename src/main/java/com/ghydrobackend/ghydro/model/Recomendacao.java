package com.ghydrobackend.ghydro.model;

import java.security.Timestamp;

import com.ghydrobackend.ghydro.model.enums.StatusRecomendacao;
import com.ghydrobackend.ghydro.model.enums.TipoAcao;

public class Recomendacao {
    private String id;
    private String plantioId;
    private Timestamp dataGeracao;
    private TipoAcao tipoAcao;
    private Float quantidade;
    // Ex: "2 horas", "30 minutos" não sei se é string
    private String duracao;
    private String observacoes;
    private String agenteResponsavel;
    private StatusRecomendacao status;
}
