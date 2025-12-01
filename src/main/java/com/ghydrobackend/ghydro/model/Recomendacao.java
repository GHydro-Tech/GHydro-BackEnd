package com.ghydrobackend.ghydro.model;

import java.security.Timestamp;

import com.ghydrobackend.ghydro.model.enums.StatusRecomendacao;
import com.ghydrobackend.ghydro.model.enums.TipoAcao;

import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

public class Recomendacao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long plantioId;

    @Column(name ="dataGeracao")
    private Timestamp dataGeracao;

    @Enumerated(EnumType.STRING)
    @Column
    private TipoAcao tipoAcao;

    @Column(name ="quantidade")
    private Integer quantidade;

    // Ex: "2 horas", "30 minutos" não sei se é string
    @Column(name ="duracao")
    private String duracao;

    @Column(name ="observacoes")
    private String observacoes;

    @Column(name ="agenteResponsavel")
    private String agenteResponsavel;

    @Enumerated(EnumType.STRING)
    @Column
    private StatusRecomendacao status;
    
    @Column(name ="dataConclusao")
    private Timestamp dataConclusao;
    
    // Ele gera uma execução de manejo associada, mas pode gerar ou não, o que faz esse atributo não existir sempre, então pode ser nulo
    private Long execucaoManejoId;
}
