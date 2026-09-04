package com.ghydrobackend.ghydro.service;

import com.ghydrobackend.ghydro.model.Sensor;
import com.ghydrobackend.ghydro.model.Setor;
import com.ghydrobackend.ghydro.repository.SensorRepository;
import com.ghydrobackend.ghydro.repository.SetorRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class SensorService {

    @Autowired
    private SensorRepository sensorRepository;

    @Autowired
    private SetorRepository setorRepository; // Necessário para validar e carregar o Setor

    public List<Sensor> listarSensor() {
        return sensorRepository.findAll();
    }

    public Sensor buscarPorId(Long id) {
        return sensorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Sensor não encontrado com o ID: " + id));
    }

    @Transactional
    public Sensor salvarSensor(Sensor sensor) {
        validarCamposObrigatorios(sensor);
        validarRegrasTecnicas(sensor);

        // Carrega o Setor completo para não retornar nulo no JSON
        carregarSetor(sensor);

        return sensorRepository.save(sensor);
    }

    @Transactional
    public Sensor atualizarSensor(Long id, Sensor sensorAtualizado) {
        Sensor sensorExistente = buscarPorId(id);

        validarCamposObrigatorios(sensorAtualizado);
        validarRegrasTecnicas(sensorAtualizado);

        // Se trocou de setor, valida e carrega o novo
        if (sensorAtualizado.getSetor() != null) {
            carregarSetor(sensorAtualizado);
            sensorExistente.setSetor(sensorAtualizado.getSetor());
        }

        // Atualiza dados técnicos
        sensorExistente.setTipos(sensorAtualizado.getTipos());
        sensorExistente.setStatus(sensorAtualizado.getStatus());
        sensorExistente.setDataInstalacao(sensorAtualizado.getDataInstalacao());
        sensorExistente.setNivelBateria(sensorAtualizado.getNivelBateria());

        return sensorRepository.save(sensorExistente);
    }

    public void deletarSensor(Long id) {
        if (!sensorRepository.existsById(id)) {
            throw new EntityNotFoundException("Sensor não encontrado para exclusão.");
        }
        sensorRepository.deleteById(id);
    }

    // --- Métodos Auxiliares ---

    private void validarCamposObrigatorios(Sensor s) {
        if (s.getStatus() == null) {
            throw new IllegalArgumentException("O status do sensor é obrigatório.");
        }
        if (s.getDataInstalacao() == null) {
            throw new IllegalArgumentException("A data de instalação é obrigatória.");
        }
        // Valida se a lista de tipos não está vazia ou nula
        if (s.getTipos() == null || s.getTipos().isEmpty()) {
            throw new IllegalArgumentException("O sensor deve possuir pelo menos um Tipo (ex: TEMPERATURA, UMIDADE).");
        }
    }

    private void validarRegrasTecnicas(Sensor s) {
        // Validação de Bateria (0 a 100%)
        if (s.getNivelBateria() != null) {
            if (s.getNivelBateria() < 0 || s.getNivelBateria() > 100) {
                throw new IllegalArgumentException("O nível de bateria deve estar entre 0 e 100.");
            }
        }

        // Validação de Data (Não pode ser futura)
        if (s.getDataInstalacao().isAfter(LocalDateTime.now())) {
            throw new IllegalArgumentException("A data de instalação não pode ser no futuro.");
        }
    }

    private void carregarSetor(Sensor sensor) {
        if (sensor.getSetor() == null || sensor.getSetor().getId() == null) {
            throw new IllegalArgumentException("É obrigatório vincular o sensor a um Setor.");
        }

        Setor setorCompleto = setorRepository.findById(sensor.getSetor().getId())
                .orElseThrow(() -> new EntityNotFoundException("O Setor informado (ID " + sensor.getSetor().getId() + ") não existe."));

        sensor.setSetor(setorCompleto);
    }
}