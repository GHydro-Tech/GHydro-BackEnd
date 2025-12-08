package com.ghydrobackend.ghydro.model;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import com.ghydrobackend.ghydro.model.enums.StatusRecomendacao;
import com.ghydrobackend.ghydro.model.enums.TipoAcao;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "recomendacao")
public class Recomendacao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "plantio_id")
    private Plantio plantio;

    @Column(name ="dataGeracao")
    private LocalDateTime dataGeracao;

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
    private LocalDateTime dataConclusao;
    
    @OneToOne(mappedBy = "recomendacao")
    private ExecucaoManejo execucao;
}
