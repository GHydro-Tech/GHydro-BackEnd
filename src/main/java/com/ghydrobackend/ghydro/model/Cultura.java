package com.ghydrobackend.ghydro.model;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

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
