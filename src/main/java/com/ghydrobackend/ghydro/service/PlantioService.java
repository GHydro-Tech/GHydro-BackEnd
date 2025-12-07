package com.ghydrobackend.ghydro.service;

import com.ghydrobackend.ghydro.exception.RegraDeNegocioException;
import com.ghydrobackend.ghydro.model.Cultura;
import com.ghydrobackend.ghydro.model.Plantio;
import com.ghydrobackend.ghydro.model.Setor;
import com.ghydrobackend.ghydro.repository.CulturaRepository;
import com.ghydrobackend.ghydro.repository.PlantioRepository;
import com.ghydrobackend.ghydro.repository.SetorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class PlantioService {

    @Autowired
    private PlantioRepository plantioRepository;

    @Autowired
    private CulturaRepository culturaRepository; // Para carregar a cultura

    @Autowired
    private SetorRepository setorRepository; // Para carregar o setor

    public List<Plantio> listarPlantio() {
        return plantioRepository.findAll();
    }

    public Plantio buscarPorId(Long id) {
        return plantioRepository.findById(id)
                .orElseThrow(() -> new RegraDeNegocioException("Plantio não encontrado com o ID: " + id));
    }

    @Transactional
    public Plantio salvarPlantio(Plantio plantio) {
        validarCamposObrigatorios(plantio);

        // 1. Carrega dependências (Cultura e Setor) para não retornar nulo
        carregarDependencias(plantio);

        // 2. Valida a lógica das datas
        validarDatas(plantio);

        return plantioRepository.save(plantio);
    }

    @Transactional
    public Plantio atualizarPlantio(Long id, Plantio plantioAtualizado) {
        Plantio plantioExistente = buscarPorId(id);

        validarCamposObrigatorios(plantioAtualizado);
        validarDatas(plantioAtualizado);

        // Se mudou a cultura
        if (plantioAtualizado.getCultura() != null) {
            Cultura cultura = culturaRepository.findById(plantioAtualizado.getCultura().getId())
                    .orElseThrow(() -> new RegraDeNegocioException("Cultura não encontrada."));
            plantioExistente.setCultura(cultura);
        }

        // Se mudou o setor
        if (plantioAtualizado.getSetor() != null) {
            Setor setor = setorRepository.findById(plantioAtualizado.getSetor().getId())
                    .orElseThrow(() -> new RegraDeNegocioException("Setor não encontrado."));
            plantioExistente.setSetor(setor);
        }

        // Atualiza dados simples
        plantioExistente.setDataPlantio(plantioAtualizado.getDataPlantio());
        plantioExistente.setDataColheitaEstimada(plantioAtualizado.getDataColheitaEstimada());
        plantioExistente.setStatusPlantio(plantioAtualizado.getStatusPlantio());

        return plantioRepository.save(plantioExistente);
    }

    public void deletarPlantio(Long id) {
        if (!plantioRepository.existsById(id)) {
            throw new RegraDeNegocioException("Plantio não encontrado para exclusão.");
        }
        plantioRepository.deleteById(id);
    }

    // --- Métodos Auxiliares ---

    private void validarCamposObrigatorios(Plantio p) {
        if (p.getDataPlantio() == null) {
            throw new RegraDeNegocioException("A data do plantio é obrigatória.");
        }
        if (p.getStatusPlantio() == null) {
            throw new RegraDeNegocioException("O status do plantio é obrigatório.");
        }
    }

    private void validarDatas(Plantio p) {
        // Se tiver data de colheita estimada, ela deve ser DEPOIS do plantio
        if (p.getDataColheitaEstimada() != null) {
            if (p.getDataColheitaEstimada().isBefore(p.getDataPlantio())) {
                throw new RegraDeNegocioException("A data estimada de colheita não pode ser anterior à data do plantio.");
            }

            if (p.getDataColheitaEstimada().isEqual(p.getDataPlantio())) {
                throw new RegraDeNegocioException("A data de colheita não pode ser no mesmo dia do plantio.");
            }
        }

    }

    private void carregarDependencias(Plantio p) {
        // Valida e carrega Cultura
        if (p.getCultura() == null || p.getCultura().getId() == null) {
            throw new RegraDeNegocioException("É obrigatório informar a Cultura.");
        }
        Cultura cultura = culturaRepository.findById(p.getCultura().getId())
                .orElseThrow(() -> new RegraDeNegocioException("A Cultura informada (ID " + p.getCultura().getId() + ") não existe."));
        p.setCultura(cultura);

        // Valida e carrega Setor
        if (p.getSetor() == null || p.getSetor().getId() == null) {
            throw new RegraDeNegocioException("É obrigatório informar o Setor.");
        }
        Setor setor = setorRepository.findById(p.getSetor().getId())
                .orElseThrow(() -> new RegraDeNegocioException("O Setor informado (ID " + p.getSetor().getId() + ") não existe."));
        p.setSetor(setor);
    }
}