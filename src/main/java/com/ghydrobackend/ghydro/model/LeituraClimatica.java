package com.ghydrobackend.ghydro.model;

import java.security.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long estacaoId;

    @Column(name ="dataHora")
    private Timestamp dataHora;

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
