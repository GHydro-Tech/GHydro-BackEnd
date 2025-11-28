package com.ghydrobackend.ghydro.model;

import java.util.List;

import com.ghydrobackend.ghydro.model.enums.StatusPlantio;

public class Plantio {
    private Long id;
    private Long culturaId;
    private String dataPlantio;
    private String dataColheitaEstimada;
    private StatusPlantio statusPlantio;
    private List<Long> manejosIds;
    private List<Long> setoresIds;
}
