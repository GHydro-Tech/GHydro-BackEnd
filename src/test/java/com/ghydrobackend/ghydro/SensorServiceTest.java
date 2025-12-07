package com.ghydrobackend.ghydro;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ghydrobackend.ghydro.exception.RegraDeNegocioException;
import com.ghydrobackend.ghydro.model.Sensor;
import com.ghydrobackend.ghydro.model.Setor;
import com.ghydrobackend.ghydro.model.enums.StatusSensor;
import com.ghydrobackend.ghydro.model.enums.TipoSensor;
import com.ghydrobackend.ghydro.repository.SensorRepository;
import com.ghydrobackend.ghydro.repository.SetorRepository;
import com.ghydrobackend.ghydro.service.SensorService;

@ExtendWith(MockitoExtension.class)
class SensorServiceTest {

    @InjectMocks
    private SensorService sensorService;

    @Mock
    private SensorRepository sensorRepository;

    @Mock
    private SetorRepository setorRepository;

    // --- Atributos para uso nos testes ---
    private Sensor sensorValido;
    private Setor setorValido;
    private final Long ID_SENSOR = 1L;
    private final Long ID_SETOR = 10L;

    // --- Configuração Inicial (Roda ANTES de cada teste) ---
    @BeforeEach
    void setUp() {
        // 1. Instancia o Setor padrão
        setorValido = new Setor();
        setorValido.setId(ID_SETOR);
        setorValido.setNome("Setor Padrão");

        // 2. Instancia o Sensor padrão com dados válidos
        sensorValido = new Sensor();
        sensorValido.setId(ID_SENSOR);
        sensorValido.setStatus(StatusSensor.ATIVO);
        sensorValido.setDataInstalacao(LocalDateTime.now());
        sensorValido.setNivelBateria(100.0);
        sensorValido.setTipos(List.of(TipoSensor.TEMPERATURA));
        sensorValido.setSetor(setorValido);
    }

    // --- Teste 1: Salvar Sensor com Sucesso ---
    @Test
    @DisplayName("Deve salvar sensor com sucesso (usando dados do setUp)")
    void deveSalvarSensorComSucesso() {
        // Cenário: O setor existe e o save retorna o próprio objeto
        when(setorRepository.findById(ID_SETOR)).thenReturn(Optional.of(setorValido));
        when(sensorRepository.save(any(Sensor.class))).thenReturn(sensorValido);

        // Ação
        Sensor resultado = sensorService.salvarSensor(sensorValido);

        // Verificação
        assertNotNull(resultado);
        assertEquals(StatusSensor.ATIVO, resultado.getStatus());
        verify(sensorRepository, times(1)).save(sensorValido);
    }

    // --- Teste 2: Validar Regra de Negócio (Bateria Inválida) ---
    @Test
    @DisplayName("Deve lançar exceção se bateria > 100")
    void deveLancarErroComBateriaInvalida() {
        // Cenário: Pegamos o sensor válido e "estragamos" apenas a bateria
        sensorValido.setNivelBateria(150.0); 

        // Ação & Verificação
        RegraDeNegocioException erro = assertThrows(RegraDeNegocioException.class, () -> {
            sensorService.salvarSensor(sensorValido);
        });

        assertEquals("O nível de bateria deve estar entre 0 e 100.", erro.getMessage());
        verify(sensorRepository, never()).save(any());
    }

    // --- Teste 3: Atualizar Sensor com Sucesso ---
    @Test
    @DisplayName("Deve atualizar sensor existente")
    void deveAtualizarSensorComSucesso() {
        // Cenário: Sensor existente vem do banco (usamos o do setUp)
        when(sensorRepository.findById(ID_SENSOR)).thenReturn(Optional.of(sensorValido));
        when(sensorRepository.save(any(Sensor.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Criamos um objeto APENAS com os dados novos para atualizar
        Sensor dadosAtualizacao = new Sensor();
        dadosAtualizacao.setStatus(StatusSensor.MANUTENCAO); // Mudou para MANUTENÇÃO
        dadosAtualizacao.setNivelBateria(80.0);              // Bateria caiu um pouco
        dadosAtualizacao.setDataInstalacao(LocalDateTime.now());
        dadosAtualizacao.setTipos(List.of(TipoSensor.UMIDADE));
        // Não setamos setor, para manter o mesmo

        // Ação
        Sensor resultado = sensorService.atualizarSensor(ID_SENSOR, dadosAtualizacao);

        // Verificação
        assertEquals(StatusSensor.MANUTENCAO, resultado.getStatus()); // Confirma a mudança
        assertEquals(80.0, resultado.getNivelBateria());
        verify(sensorRepository, times(1)).save(any(Sensor.class));
    }

    // --- Teste 4: Deletar Sensor com Sucesso ---
    @Test
    @DisplayName("Deve deletar sensor por ID")
    void deveDeletarSensorComSucesso() {
        // Cenário
        when(sensorRepository.existsById(ID_SENSOR)).thenReturn(true);

        // Ação
        sensorService.deletarSensor(ID_SENSOR);

        // Verificação
        verify(sensorRepository, times(1)).deleteById(ID_SENSOR);
    }
}
