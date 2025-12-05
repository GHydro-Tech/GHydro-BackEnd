package com.ghydrobackend.ghydro.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
@Table(name = "setor")
public class Setor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    
    @Column(columnDefinition = "TEXT")
    private String poligonoGeografico;

    @ManyToOne
    @JoinColumn(name = "propriedade_id")
    private Propriedade propriedade;

    @ManyToOne
    @JoinColumn(name = "tipo_solo_id")
    private TipoSolo tipoSolo;

    @ManyToOne
    @JoinColumn(name = "dispositivo_id")
    private DispositivoIrrigacao dispositivoIrrigacao;

    @OneToMany(mappedBy = "setor")
    private List<Plantio> plantios = new ArrayList<>();

    @OneToMany(mappedBy = "setor")
    private List<Sensor> sensores = new ArrayList<>();
}
