package com.ghydrobackend.ghydro.model;

import java.util.List;

public class Setor {
    private Long id;
    private String nome;
    private String poligonoGeografico;
    private Long propriedadeId;
    private Long plantioId;
    private List<Long> sensoresIds;
    // n:n
    // mas n faz sentido ter a lista de solos, como resolver isso?
    private List<Long> solosIds;
    private List<Long> dispositivosIds;

}
