package com.ghydrobackend.ghydro.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ghydrobackend.ghydro.model.enums.Moeda;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
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
@Table(name = "configuracaoCusto")
public class ConfiguracaoCusto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "propriedade_id", unique = true)
    @JsonIgnore
    private Propriedade propriedade;

    @Column(name = "custoM3Agua")
    private Double custoM3Agua;

    @Column(name = "custoKWh")
    private Double custoKWh;

    @Enumerated(EnumType.STRING)
    @Column
    private Moeda moeda;

}
