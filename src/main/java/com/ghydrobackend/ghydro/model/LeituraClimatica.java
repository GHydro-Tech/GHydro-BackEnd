package com.ghydrobackend.ghydro.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
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
@Table(name = "leituraClimatica")
public class LeituraClimatica {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "estacao_id")
    private EstacaoMetereologica estacaoMetereologica;

    @Column(name ="dataHora")
    private LocalDateTime dataHora;

    @Column(name ="temperaturaMaxima")
    private Double temperaturaMaxima;

    @Column(name ="temperaturaMinima")
    private Double temperaturaMinima;

    @Column(name ="umidadeRelativaAr")
    private Double umidadeRelativaAr;

    @Column(name ="velocidadeVento")
    private Double velocidadeVento;

    @Column(name ="radiacaoSolar")
    private Double radiacaoSolar;

    @Column(name ="precipitacao")
    private Double precipitacao;
    
    @Column(name ="etoCalculado")
    private Double etoCalculado;
}
