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
@Table(name = "setor")
public class Setor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name ="nome")
    private String nome;
    
    @Column(name ="poligonoGeografico")
    private String poligonoGeografico;

    private Long propriedadeId;
    private Long plantioId;
    private List<Long> sensoresIds;
    // n:n
    // mas n faz sentido ter a lista de solos, como resolver isso?
    private List<Long> solosIds;
    private List<Long> dispositivosIds;

}
