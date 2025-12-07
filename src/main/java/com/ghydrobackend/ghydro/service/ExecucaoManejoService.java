package com.ghydrobackend.ghydro.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ghydrobackend.ghydro.exception.RegraDeNegocioException;
import com.ghydrobackend.ghydro.model.ExecucaoManejo;
import com.ghydrobackend.ghydro.model.Plantio;
import com.ghydrobackend.ghydro.model.Recomendacao;
import com.ghydrobackend.ghydro.repository.ExecucaoManejoRepository;
import com.ghydrobackend.ghydro.repository.PlantioRepository;
import com.ghydrobackend.ghydro.repository.RecomendacaoRepository;

@Service
public class ExecucaoManejoService {

    @Autowired
    private ExecucaoManejoRepository execucaoRepository;

    @Autowired
    private PlantioRepository plantioRepository;

    @Autowired
    private RecomendacaoRepository recomendacaoRepository;

    public List<ExecucaoManejo> listarExecucaoManejo() {
        return execucaoRepository.findAll();
    }

    public ExecucaoManejo buscarPorId(Long id) {
        return execucaoRepository.findById(id)
                .orElseThrow(() -> new RegraDeNegocioException("Execução de manejo não encontrada para o ID: " + id));
    }

    @Transactional
    public ExecucaoManejo salvarExecucaoManejo(ExecucaoManejo execucao) {

        validarCamposObrigatorios(execucao);
        validarDatas(execucao);
        validarValoresFisicos(execucao);
        carregarDependencias(execucao); // carrega plantio e recomendação

        return execucaoRepository.save(execucao);
    }

    @Transactional
    public ExecucaoManejo atualizarExecucaoManejo(Long id, ExecucaoManejo execucaoAtualizada) {
        ExecucaoManejo existente = buscarPorId(id);

        validarCamposObrigatorios(execucaoAtualizada);
        validarDatas(execucaoAtualizada);
        validarValoresFisicos(execucaoAtualizada);
        carregarDependencias(execucaoAtualizada);

        // Atualiza campos
        existente.setInicio(execucaoAtualizada.getInicio());
        existente.setFim(execucaoAtualizada.getFim());
        existente.setVolumeAguaAplicado(execucaoAtualizada.getVolumeAguaAplicado());
        existente.setEnergiaGasta(execucaoAtualizada.getEnergiaGasta());
        existente.setOrigem(execucaoAtualizada.getOrigem());
        existente.setPlantio(execucaoAtualizada.getPlantio());
        existente.setRecomendacao(execucaoAtualizada.getRecomendacao());

        return execucaoRepository.save(existente);
    }

    public void deletarExecucaoManejo(Long id) {
        if (!execucaoRepository.existsById(id)) {
            throw new RegraDeNegocioException("Execução de manejo não encontrada para exclusão.");
        }
        execucaoRepository.deleteById(id);
    }

    // ---------- MÉTODOS AUXILIARES -----------

    private void validarCamposObrigatorios(ExecucaoManejo e) {
        if (e.getInicio() == null) {
            throw new RegraDeNegocioException("A data de início é obrigatória.");
        }
        if (e.getOrigem() == null) {
            throw new RegraDeNegocioException("A origem do manejo é obrigatória.");
        }
    }

    private void validarDatas(ExecucaoManejo e) {

        if (e.getInicio().isAfter(LocalDateTime.now())) {
            throw new RegraDeNegocioException("A data de início não pode ser futura.");
        }

        if (e.getFim() != null && e.getFim().isBefore(e.getInicio())) {
            throw new RegraDeNegocioException("A data de fim não pode ser anterior ao início.");
        }
    }

    private void validarValoresFisicos(ExecucaoManejo e) {
        if (e.getVolumeAguaAplicado() != null && e.getVolumeAguaAplicado() < 0) {
            throw new RegraDeNegocioException("O volume de água aplicado não pode ser negativo.");
        }
        if (e.getEnergiaGasta() != null && e.getEnergiaGasta() < 0) {
            throw new RegraDeNegocioException("A energia gasta não pode ser negativa.");
        }
    }

    private void carregarDependencias(ExecucaoManejo e) {

        // Validar e carregar Plantio
        if (e.getPlantio() == null || e.getPlantio().getId() == null) {
            throw new RegraDeNegocioException("É obrigatório informar o plantio.");
        }

        Plantio plantio = plantioRepository.findById(e.getPlantio().getId())
                .orElseThrow(() -> new RegraDeNegocioException("Plantio informado não existe."));
        e.setPlantio(plantio);

        // Recomendação é opcional, mas se vier precisa existir
        if (e.getRecomendacao() != null && e.getRecomendacao().getId() != null) {
            Recomendacao rec = recomendacaoRepository.findById(e.getRecomendacao().getId())
                    .orElseThrow(() -> new RegraDeNegocioException("Recomendação informada não existe."));
            e.setRecomendacao(rec);
        }
    }
}