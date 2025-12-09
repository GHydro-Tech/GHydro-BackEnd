package com.ghydrobackend.ghydro.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
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
@Table(name = "tipoSolo")
public class TipoSolo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name ="descricao")
    private String descricao;

    @Column(name ="capacidadeCampo")
    private Double capacidadeCampo;

    @Column(name ="pontoMurcha")
    private Double pontoMurcha;

    @Column(name ="densidadeAparente")
    private Double densidadeAparente;

    @Column(name ="taxaInfiltracaoBasica")
    private Double taxaInfiltracaoBasica;

    @OneToMany(mappedBy = "tipoSolo")
    @JsonIgnore
    private List<Setor> setores = new ArrayList<>();
}
