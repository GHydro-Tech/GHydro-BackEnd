package com.ghydrobackend.ghydro.model;


import java.security.Timestamp;
import java.util.List;

import com.ghydrobackend.ghydro.model.enums.StatusSensor;
import com.ghydrobackend.ghydro.model.enums.TipoSensor;

public class Sensor {
    private Long id;
    private List<TipoSensor> tipos;
    private StatusSensor status;
    private Timestamp dataInstalacao;
    private Float nivelBateria;
    private Long setorID;
    private List<Long> leiturasIds;
}
