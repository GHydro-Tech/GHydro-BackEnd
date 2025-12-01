package com.ghydrobackend.ghydro.model;

import java.util.List;

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
@Table(name = "cultura")
public class Cultura {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name ="nomeCientifico")
    private String nomeCientifico;

    @Column(name ="nomePopular")
    private String nomePopular;

    @Column(name ="variedade")
    private String variedade;
    
    // Lista de estados fenotípicos associados à cultura
    private List<Long> estadosFenoticos;
}
