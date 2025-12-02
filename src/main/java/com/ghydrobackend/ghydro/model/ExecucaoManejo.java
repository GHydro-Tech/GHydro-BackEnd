package com.ghydrobackend.ghydro.model;

import java.security.Timestamp;

import com.ghydrobackend.ghydro.model.enums.Origem;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
@Table(name = "execucaoManejo")
public class ExecucaoManejo {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long plantioId;

    private Long recomendacaoId;
    
    @Column(name ="inicio")
    private Timestamp inicio;

    @Column(name ="fim")
    private Timestamp fim;

    @Column(name ="volumeAguaAplicado")
    private Double volumeAguaAplicado;

    @Column(name ="energiaGasta")
    private Double energiaGasta;
    
    @Enumerated(EnumType.STRING)
    @Column
    private Origem origem;
}
