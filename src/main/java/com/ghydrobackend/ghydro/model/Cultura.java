package com.ghydrobackend.ghydro.model;

import java.util.List;

public class Cultura {
    private Long id;
    private String nomeCientifico;
    private String nomePopular;
    private String variedade;
    // Lista de estados fenotípicos associados à cultura
    private List<Long> estadosFenoticos;
}
