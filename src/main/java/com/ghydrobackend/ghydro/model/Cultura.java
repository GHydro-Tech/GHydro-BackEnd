package com.ghydrobackend.ghydro.model;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;
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
@Table(name = "cultura")
public class Cultura {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name ="nomeCientifico")
    private String nomeCientifico;

    @Column(name ="nomePopular")
    private String nomePopular;

    @Column(name ="variedade")
    private String variedade;
    
    @OneToMany(mappedBy = "cultura", cascade = CascadeType.ALL)
    @OrderBy("ordemSequencia ASC")
    private List<EstadoFenologico> estadosFenoticos;
}
