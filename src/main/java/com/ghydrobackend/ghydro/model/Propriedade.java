package com.ghydrobackend.ghydro.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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
@Table(name = "propriedade")
public class Propriedade {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name ="nome")
    private String nome;

    @Column(name ="localizacao")
    private String localizacao;

    @OneToOne(mappedBy = "propriedade", cascade = CascadeType.ALL)
    private ConfiguracaoCusto configuracaoCusto;

    @ManyToOne
    @JoinColumn(name = "proprietario_id", nullable = false)
    private Proprietario proprietario;

    @OneToMany(mappedBy = "propriedade", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Setor> setores = new ArrayList<>();
    
    @ManyToMany
    @JoinTable(
        name = "propriedade_estacao",
        joinColumns = @JoinColumn(name = "propriedade_id"),
        inverseJoinColumns = @JoinColumn(name = "estacao_id")
    )
    private List<EstacaoMetereologica> estacoes = new ArrayList<>();
}
