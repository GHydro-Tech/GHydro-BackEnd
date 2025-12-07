package com.ghydrobackend.ghydro;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
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
public class SensorServiceTest {

    @InjectMocks
    private SensorService sensorService;

    @Mock
    private SensorRepository sensorRepository;

    @Mock
    private SetorRepository setorRepository;

    // --- Teste 1: Salvar Sensor com Sucesso ---
    @Test
    @DisplayName("Deve salvar um sensor com sucesso quando os dados forem válidos e o setor existir")
    void deveSalvarSensorComSucesso() {
        // Cenário
        Long setorId = 1L;
        Setor setorMock = new Setor();
        setorMock.setId(setorId);

        Sensor sensorEntrada = new Sensor();
        sensorEntrada.setStatus(StatusSensor.ATIVO);
        sensorEntrada.setDataInstalacao(LocalDateTime.now());
        sensorEntrada.setNivelBateria(100.0);
        sensorEntrada.setTipos(List.of(TipoSensor.TEMPERATURA));
        sensorEntrada.setSetor(setorMock);

        // Mock: Simula que o setor existe no banco e que o sensor será salvo
        when(setorRepository.findById(setorId)).thenReturn(Optional.of(setorMock));
        when(sensorRepository.save(any(Sensor.class))).thenReturn(sensorEntrada);

        // Ação
        Sensor sensorSalvo = sensorService.salvarSensor(sensorEntrada);

        // Verificação
        assertNotNull(sensorSalvo);
        assertEquals(StatusSensor.ATIVO, sensorSalvo.getStatus());
        verify(setorRepository, times(1)).findById(setorId);
        verify(sensorRepository, times(1)).save(sensorEntrada);
    }

    // --- Teste 2: Validar Regra de Negócio (Bateria Inválida) ---
    @Test
    @DisplayName("Deve lançar exceção se a bateria for maior que 100%")
    void deveLancarErroComBateriaInvalida() {
        // Cenário
        Sensor sensor = new Sensor();
        sensor.setStatus(StatusSensor.ATIVO);
        sensor.setDataInstalacao(LocalDateTime.now());
        sensor.setTipos(List.of(TipoSensor.UMIDADE));
        sensor.setNivelBateria(150.0); // Inválido (> 100)

        // Ação & Verificação
        RegraDeNegocioException exception = assertThrows(RegraDeNegocioException.class, () -> {
            sensorService.salvarSensor(sensor);
        });

        assertEquals("O nível de bateria deve estar entre 0 e 100.", exception.getMessage());
        // Garante que o método save NUNCA foi chamado
        verify(sensorRepository, never()).save(any());
    }

    // --- Teste 3: Atualizar Sensor com Sucesso ---
    @Test
    @DisplayName("Deve atualizar os dados de um sensor existente")
    void deveAtualizarSensorComSucesso() {
        // Cenário
        Long idSensor = 1L;
        
        // Sensor antigo (no banco)
        Sensor sensorExistente = new Sensor();
        sensorExistente.setId(idSensor);
        sensorExistente.setStatus(StatusSensor.ATIVO);
        sensorExistente.setNivelBateria(50.0);

        // Dados novos para atualização
        Sensor sensorNovosDados = new Sensor();
        sensorNovosDados.setStatus(StatusSensor.MANUTENCAO);
        sensorNovosDados.setDataInstalacao(LocalDateTime.now());
        sensorNovosDados.setTipos(List.of(TipoSensor.PRESSAO));
        sensorNovosDados.setNivelBateria(45.0);
        // Sem alterar setor

        when(sensorRepository.findById(idSensor)).thenReturn(Optional.of(sensorExistente));
        when(sensorRepository.save(any(Sensor.class))).thenAnswer(i -> i.getArgument(0));

        // Ação
        Sensor resultado = sensorService.atualizarSensor(idSensor, sensorNovosDados);

        // Verificação
        assertEquals(StatusSensor.MANUTENCAO, resultado.getStatus());
        assertEquals(45.0, resultado.getNivelBateria());
        verify(sensorRepository, times(1)).save(sensorExistente);
    }

    // --- Teste 4: Deletar Sensor com Sucesso ---
    @Test
    @DisplayName("Deve deletar o sensor se o ID existir")
    void deveDeletarSensorComSucesso() {
        // Cenário
        Long idParaDeletar = 10L;
        when(sensorRepository.existsById(idParaDeletar)).thenReturn(true);

        // Ação
        sensorService.deletarSensor(idParaDeletar);

        // Verificação
        verify(sensorRepository, times(1)).deleteById(idParaDeletar);
    }
    
}
