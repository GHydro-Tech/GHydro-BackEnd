package com.ghydrobackend.ghydro.model;

import java.time.LocalDateTime;

import com.ghydrobackend.ghydro.model.enums.Origem;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Table(name = "execucaoManejo")
public class ExecucaoManejo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "plantio_id")
    private Plantio plantio;

    @OneToOne
    @JoinColumn(name = "recomendacao_id", nullable = true)
    private Recomendacao recomendacao;
    
    @Column(name ="inicio")
    private LocalDateTime inicio;

    @Column(name ="fim")
    private LocalDateTime fim;

    @Column(name ="volumeAguaAplicado")
    private Double volumeAguaAplicado;

    @Column(name ="energiaGasta")
    private Double energiaGasta;
    
    @Enumerated(EnumType.STRING)
    @Column
    private Origem origem;
}
