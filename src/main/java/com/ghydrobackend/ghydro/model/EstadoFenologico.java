package com.ghydrobackend.ghydro.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Table(name = "estadoFenologico")
public class EstadoFenologico {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "culturaId", nullable = false)
    private Cultura cultura;

    @Column(name ="ordemSequencia")
    private Integer ordemSequencia;

    @Column(name ="nomeFase")
    private String nomeFase;

    @Column(name ="descricaoFase")
    private String descricaoFase;

    @Column(name ="duracaoDias")
    private Integer duracaoDias;

    @Column(name ="kCFase")
    private Double kCFase;

    @Column(name ="profundidadeRaiz_cm")
    private Double profundidadeRaiz_cm;
    
    @Column(name ="sensibilidadeKY")
    private Double sensibilidadeKY;
}
