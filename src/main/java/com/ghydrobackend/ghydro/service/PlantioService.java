package com.ghydrobackend.ghydro.service;

import com.ghydrobackend.ghydro.exception.RegraDeNegocioException;
import com.ghydrobackend.ghydro.model.Cultura;
import com.ghydrobackend.ghydro.model.Plantio;
import com.ghydrobackend.ghydro.model.Setor;
import com.ghydrobackend.ghydro.model.enums.StatusPlantio;
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
    private CulturaRepository culturaRepository;

    @Autowired
    private SetorRepository setorRepository;

    // LISTAR
    public List<Plantio> listarPlantio() {
        return plantioRepository.findAll();
    }

    // BUSCAR POR ID
    public Plantio buscarPorId(Long id) {
        return plantioRepository.findById(id)
                .orElseThrow(() -> new RegraDeNegocioException("Plantio não encontrado com o ID: " + id));
    }

    // SALVAR
    @Transactional
    public Plantio salvarPlantio(Plantio plantio) {
        validarCamposObrigatorios(plantio);
        validarDatas(plantio);
        carregarDependencias(plantio); // evita retornar null no JSON

        // Status inicial obrigatório
        if (plantio.getStatusPlantio() == null) {
            plantio.setStatusPlantio(StatusPlantio.PLANEJADO);
        }

        return plantioRepository.save(plantio);
    }

    // ATUALIZAR
    @Transactional
    public Plantio atualizarPlantio(Long id, Plantio plantioAtualizado) {

        Plantio plantioExistente = buscarPorId(id);

        validarCamposObrigatorios(plantioAtualizado);
        validarDatas(plantioAtualizado);

        // ------------------------------
        //  REGRAS DE TRANSIÇÃO DE STATUS
        // ------------------------------

        StatusPlantio antigo = plantioExistente.getStatusPlantio();
        StatusPlantio novo = plantioAtualizado.getStatusPlantio();

        // Não permitir retroceder status quando já CONCLUÍDO
        if (antigo == StatusPlantio.CONCLUIDO && novo != StatusPlantio.CONCLUIDO) {
            throw new RegraDeNegocioException("Não é possível alterar um plantio que já foi CONCLUÍDO.");
        }

        // Não permite pular de PLANEJADO direto para CONCLUIDO
        if (antigo == StatusPlantio.PLANEJADO && novo == StatusPlantio.CONCLUIDO) {
            throw new RegraDeNegocioException(
                    "O plantio deve estar EM_ANDAMENTO antes de ser CONCLUÍDO."
            );
        }

        // ------------------------------
        // DEPENDÊNCIAS
        // ------------------------------

        if (plantioAtualizado.getCultura() != null) {
            Cultura c = culturaRepository.findById(plantioAtualizado.getCultura().getId())
                    .orElseThrow(() -> new RegraDeNegocioException("Cultura não encontrada."));
            plantioExistente.setCultura(c);
        }

        if (plantioAtualizado.getSetor() != null) {
            Setor s = setorRepository.findById(plantioAtualizado.getSetor().getId())
                    .orElseThrow(() -> new RegraDeNegocioException("Setor não encontrado."));
            plantioExistente.setSetor(s);
        }

        // ------------------------------
        // CAMPOS SIMPLES
        // ------------------------------
        plantioExistente.setDataPlantio(plantioAtualizado.getDataPlantio());
        plantioExistente.setDataColheitaEstimada(plantioAtualizado.getDataColheitaEstimada());
        plantioExistente.setStatusPlantio(plantioAtualizado.getStatusPlantio());

        return plantioRepository.save(plantioExistente);
    }

    // DELETAR
    public void deletarPlantio(Long id) {
        if (!plantioRepository.existsById(id)) {
            throw new RegraDeNegocioException("Plantio não encontrado para exclusão.");
        }
        plantioRepository.deleteById(id);
    }

    // -----------------------------------
    // MÉTODOS AUXILIARES
    // -----------------------------------

    private void validarCamposObrigatorios(Plantio p) {
        if (p.getDataPlantio() == null) {
            throw new RegraDeNegocioException("A data do plantio é obrigatória.");
        }
        if (p.getStatusPlantio() == null) {
            throw new RegraDeNegocioException("O status do plantio é obrigatório.");
        }
        if (p.getCultura() == null || p.getCultura().getId() == null) {
            throw new RegraDeNegocioException("A cultura é obrigatória.");
        }
        if (p.getSetor() == null || p.getSetor().getId() == null) {
            throw new RegraDeNegocioException("O setor é obrigatório.");
        }
    }

    private void validarDatas(Plantio p) {
        LocalDate plantio = p.getDataPlantio();
        LocalDate colheita = p.getDataColheitaEstimada();

        if (colheita != null) {
            if (colheita.isBefore(plantio)) {
                throw new RegraDeNegocioException("A data estimada de colheita não pode ser antes da data de plantio.");
            }
            if (colheita.isEqual(plantio)) {
                throw new RegraDeNegocioException("A colheita não pode ocorrer no mesmo dia do plantio.");
            }
        }
    }

    private void carregarDependencias(Plantio p) {
        // Cultura
        Cultura cultura = culturaRepository.findById(p.getCultura().getId())
                .orElseThrow(() -> new RegraDeNegocioException("Cultura informada não existe."));
        p.setCultura(cultura);

        // Setor
        Setor setor = setorRepository.findById(p.getSetor().getId())
                .orElseThrow(() -> new RegraDeNegocioException("Setor informado não existe."));
        p.setSetor(setor);
    }
}