package com.ghydrobackend.ghydro.model;

import com.ghydrobackend.ghydro.model.enums.TipoManutencao;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "manutencoes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Manutencao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // ID numérico do Sensor, Pivô, etc.
    @Column(nullable = false)
    private Long equipamentoId;

    // Para saber o que é esse ID (Ex: "SENSOR_UMIDADE", "ESP32", "PIVO")
    @Column(nullable = false)
    private String tipoEquipamento;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoManutencao tipoManutencao;

    @Column(nullable = false)
    private LocalDate dataManutencao;
    
    private String descricaoProblema;
    private String solucaoAplicada;
    
    // Nome ou email do técnico que foi a campo resolver
    private String tecnicoResponsavel; 
}