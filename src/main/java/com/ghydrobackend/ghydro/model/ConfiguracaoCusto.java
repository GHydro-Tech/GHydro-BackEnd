package com.ghydrobackend.ghydro.model;

import com.ghydrobackend.ghydro.model.enums.Moeda;

import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

public class ConfiguracaoCusto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Relacionamento com Propriedade
    private Double propriedadeId;

    @Column(name ="custoM3Agua")
    private Double custoM3Agua;

    @Column(name ="custoKWh")
    private Double custoKWh;

    @Enumerated(EnumType.STRING)
    @Column
    private Moeda moeda;

}
