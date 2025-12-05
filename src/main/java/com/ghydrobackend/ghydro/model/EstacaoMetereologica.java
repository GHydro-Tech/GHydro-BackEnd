package com.ghydrobackend.ghydro.model;

import java.util.ArrayList;
import java.util.List;

import com.ghydrobackend.ghydro.model.enums.TipoEstacao;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
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
@Table(name = "estacaoMetereologica")
public class EstacaoMetereologica {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name ="nome")
    private String nome;

    @Enumerated(EnumType.STRING)
    @Column
    private TipoEstacao tipo;

    @Column(name ="apiSource")
    private String apiSource;

    @Column(name ="latitude")
    private String latitude;

    @Column(name ="longitude")
    private String longitude;

    @Column(name ="apiKey")
    private String apiKey;

    @OneToMany(mappedBy = "estacaoMetereologica", cascade = CascadeType.ALL)
    private List<LeituraClimatica> leiturasClimaticas = new ArrayList<>();

    @ManyToMany(mappedBy = "estacoes")
    private List<Propriedade> propriedades = new ArrayList<>();
}
