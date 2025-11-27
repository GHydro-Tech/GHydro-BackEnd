package com.ghydrobackend.ghydro.model;

import java.security.Timestamp;

import com.ghydrobackend.ghydro.model.enums.Origem;

public class ExecucaoManejo {
    private String id;
    private String plantio_id;
    private Timestamp inicio;
    private Timestamp fim;
    private Double volumeAguaAplicado;
    private Double energiaGasta;
    private Origem origem;
}
