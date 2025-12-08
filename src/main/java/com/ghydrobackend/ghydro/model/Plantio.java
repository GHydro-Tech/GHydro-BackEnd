package com.ghydrobackend.ghydro.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ghydrobackend.ghydro.model.enums.StatusPlantio;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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
@Table(name = "plantio")
public class Plantio {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "cultura_id")
    private Cultura cultura;

    @Column(name ="dataPlantio")
    private LocalDate dataPlantio;
    
    @Column(name ="dataColheitaEstimada")
    private LocalDate dataColheitaEstimada;

    @Enumerated(EnumType.STRING)
    @Column
    private StatusPlantio statusPlantio;

    @OneToMany(mappedBy = "plantio", cascade = CascadeType.ALL)
    private List<ExecucaoManejo> manejos = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "setor_id")
    @JsonIgnore
    private Setor setor;
}
