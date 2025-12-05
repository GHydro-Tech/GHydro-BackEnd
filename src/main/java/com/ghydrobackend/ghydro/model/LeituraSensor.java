package com.ghydrobackend.ghydro.model;

import java.time.LocalDateTime;

import com.ghydrobackend.ghydro.model.enums.UnidadeMedida;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Table(name = "leituraSensor")
public class LeituraSensor {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sensor_id")
    private Sensor sensor;

    @Column(name ="timestamp")
    private LocalDateTime timestamp;

    @Column(name ="valorBruto")
    private double valorBruto;

    @Column(name ="valorTratado")
    private double valorTratado;
    
    @Enumerated(EnumType.STRING)
    @Column
    private UnidadeMedida unidadeMedida;
}
