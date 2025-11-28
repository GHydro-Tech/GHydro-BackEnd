package com.ghydrobackend.ghydro.model;

import java.util.List;

public class TipoSolo {
    private Long id;
    private String descricao;
    private Float capacidadeCampo;
    private Float pontoMurcha;
    private Float densidadeAparente;
    private Float taxaInfiltracaoBasica;
    // n:n
    // revisar isso
    private List<Long> SetoresIds;
}
