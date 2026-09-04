package com.ghydrobackend.ghydro.service;

import com.ghydrobackend.ghydro.model.Plantio;
import com.ghydrobackend.ghydro.model.Recomendacao;
import com.ghydrobackend.ghydro.model.enums.StatusRecomendacao;
import com.ghydrobackend.ghydro.repository.PlantioRepository;
import com.ghydrobackend.ghydro.repository.RecomendacaoRepository;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class RecomendacaoService {

    @Autowired
    private RecomendacaoRepository recomendacaoRepository;

    @Autowired
    private PlantioRepository plantioRepository;

    public List<Recomendacao> listarRecomendacao() {
        return recomendacaoRepository.findAll();
    }

    public Recomendacao buscarPorId(Long id) {
        return recomendacaoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Recomendação não encontrada pelo ID: " + id));
    }

    @Transactional
    public Recomendacao salvarRecomendacao(Recomendacao recomendacao) {

        validarCamposObrigatorios(recomendacao);
        validarDatas(recomendacao);
        validarValores(recomendacao);
        carregarPlantio(recomendacao);

        return recomendacaoRepository.save(recomendacao);
    }

    @Transactional
    public Recomendacao atualizarRecomendacao(Long id, Recomendacao recomendacaoAtualizada) {

        Recomendacao existente = buscarPorId(id);

        validarCamposObrigatorios(recomendacaoAtualizada);
        validarDatas(recomendacaoAtualizada);
        validarValores(recomendacaoAtualizada);
        carregarPlantio(recomendacaoAtualizada);

        existente.setPlantio(recomendacaoAtualizada.getPlantio());
        existente.setDataGeracao(recomendacaoAtualizada.getDataGeracao());
        existente.setTipoAcao(recomendacaoAtualizada.getTipoAcao());
        existente.setQuantidade(recomendacaoAtualizada.getQuantidade());
        existente.setDuracao(recomendacaoAtualizada.getDuracao());
        existente.setObservacoes(recomendacaoAtualizada.getObservacoes());
        existente.setAgenteResponsavel(recomendacaoAtualizada.getAgenteResponsavel());
        existente.setStatus(recomendacaoAtualizada.getStatus());
        existente.setDataConclusao(recomendacaoAtualizada.getDataConclusao());

        return recomendacaoRepository.save(existente);
    }

    public void deletarRecomendacao(Long id) {
        if (!recomendacaoRepository.existsById(id)) {
            throw new EntityNotFoundException("Recomendação não encontrada para exclusão.");
        }
        recomendacaoRepository.deleteById(id);
    }


    // ------------------- MÉTODOS AUXILIARES -------------------

    private void validarCamposObrigatorios(Recomendacao r) {

        if (r.getPlantio() == null || r.getPlantio().getId() == null) {
            throw new IllegalArgumentException("É obrigatório informar o plantio relacionado.");
        }

        if (r.getTipoAcao() == null) {
            throw new IllegalArgumentException("O tipo da ação é obrigatório.");
        }

        if (r.getStatus() == null) {
            throw new IllegalArgumentException("O status da recomendação é obrigatório.");
        }
    }

    private void validarDatas(Recomendacao r) {

        LocalDateTime geracao = r.getDataGeracao();
        LocalDateTime conclusao = r.getDataConclusao();

        if (geracao != null && geracao.isAfter(LocalDateTime.now())) {
            throw new IllegalArgumentException("A data de geração não pode ser no futuro.");
        }

        if (conclusao != null && conclusao.isBefore(geracao)) {
            throw new IllegalArgumentException("A data de conclusão não pode ser anterior à geração.");
        }

        // Se status for EXECUTADA_AUTOMATICA → precisa ter conclusão
        if (r.getStatus() == StatusRecomendacao.EXECUTADA_AUTOMATICA && conclusao == null) {
            throw new IllegalArgumentException("Recomendações automáticas executadas devem ter a data de conclusão.");
        }

        // Se NÃO for executada, limpeza opcional
        if (r.getStatus() != StatusRecomendacao.EXECUTADA_AUTOMATICA) {
            r.setDataConclusao(null);
        }
    }

    private void validarValores(Recomendacao r) {
        if (r.getQuantidade() != null && r.getQuantidade() <= 0) {
            throw new IllegalArgumentException("A quantidade deve ser maior que zero.");
        }
    }

    private void carregarPlantio(Recomendacao r) {
        Plantio p = plantioRepository.findById(r.getPlantio().getId())
                .orElseThrow(() -> new EntityNotFoundException("O plantio informado não existe."));
        r.setPlantio(p);
    }
}