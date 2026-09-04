package com.ghydrobackend.ghydro.service;

import com.ghydrobackend.ghydro.model.LeituraSensor;
import com.ghydrobackend.ghydro.model.Sensor;
import com.ghydrobackend.ghydro.repository.LeituraSensorRepository;
import com.ghydrobackend.ghydro.repository.SensorRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class LeituraSensorService {

    @Autowired
    private LeituraSensorRepository leituraRepository;

    @Autowired
    private SensorRepository sensorRepository;

    public List<LeituraSensor> listarLeituraSensor() {
        return leituraRepository.findAll();
    }

    public LeituraSensor buscarPorId(Long id) {
        return leituraRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Leitura de sensor não encontrada com o ID: " + id));
    }

    @Transactional
    public LeituraSensor salvarLeituraSensor(LeituraSensor leitura) {
        validarCamposObrigatorios(leitura);

        // 1. Carrega o Sensor completo
        carregarSensor(leitura);

        // 2. Evita duplicidade de horário para o mesmo sensor
        if (leituraRepository.existsBySensorIdAndTimestamp(
                leitura.getSensor().getId(),
                leitura.getTimestamp())) {
            throw new IllegalArgumentException("Já existe uma leitura registrada para este sensor neste exato momento.");
        }

        return leituraRepository.save(leitura);
    }

    @Transactional
    public LeituraSensor atualizarLeituraSensor(Long id, LeituraSensor leituraAtualizada) {
        LeituraSensor existente = buscarPorId(id);

        validarCamposObrigatorios(leituraAtualizada);

        // Se mudou o sensor (raríssimo em telemetria, mas possível em correção manual)
        if (leituraAtualizada.getSensor() != null) {
            carregarSensor(leituraAtualizada);
            existente.setSensor(leituraAtualizada.getSensor());
        }

        // Atualiza dados
        existente.setTimestamp(leituraAtualizada.getTimestamp());
        existente.setValorBruto(leituraAtualizada.getValorBruto());
        existente.setValorTratado(leituraAtualizada.getValorTratado());
        existente.setUnidadeMedida(leituraAtualizada.getUnidadeMedida());

        return leituraRepository.save(existente);
    }

    public void deletarLeituraSensor(Long id) {
        if (!leituraRepository.existsById(id)) {
            throw new EntityNotFoundException("Leitura não encontrada para exclusão.");
        }
        leituraRepository.deleteById(id);
    }

    // --- Métodos Auxiliares ---

    private void validarCamposObrigatorios(LeituraSensor l) {
        if (l.getTimestamp() == null) {
            throw new IllegalArgumentException("O timestamp (data/hora) da leitura é obrigatório.");
        }
        if (l.getTimestamp().isAfter(LocalDateTime.now())) {
            throw new IllegalArgumentException("A data da leitura não pode ser no futuro.");
        }
        if (l.getUnidadeMedida() == null) {
            throw new IllegalArgumentException("A unidade de medida é obrigatória.");
        }
    }

    private void carregarSensor(LeituraSensor l) {
        if (l.getSensor() == null || l.getSensor().getId() == null) {
            throw new IllegalArgumentException("É obrigatório informar o Sensor.");
        }

        Sensor sensor = sensorRepository.findById(l.getSensor().getId())
                .orElseThrow(() -> new EntityNotFoundException("O Sensor informado não existe."));

        l.setSensor(sensor);
    }
}