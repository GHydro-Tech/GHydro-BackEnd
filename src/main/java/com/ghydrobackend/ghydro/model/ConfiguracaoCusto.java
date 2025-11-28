package com.ghydrobackend.ghydro.model;

import com.ghydrobackend.ghydro.model.enums.Moeda;

public class ConfiguracaoCusto {
    private Long id;
    // Relacionamento com Propriedade
    private Double propriedadeId;
    private Double custoM3Agua;
    private Double custoKWh;
    private Moeda moeda;

    
}
