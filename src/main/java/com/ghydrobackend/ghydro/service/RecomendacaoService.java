package com.ghydrobackend.ghydro.service;

import com.ghydrobackend.ghydro.exception.RegraDeNegocioException;
import com.ghydrobackend.ghydro.model.Plantio;
import com.ghydrobackend.ghydro.model.Recomendacao;
import com.ghydrobackend.ghydro.model.enums.StatusRecomendacao;
import com.ghydrobackend.ghydro.repository.PlantioRepository;
import com.ghydrobackend.ghydro.repository.RecomendacaoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
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
                .orElseThrow(() -> new RegraDeNegocioException("Recomendação não encontrada pelo ID: " + id));
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
            throw new RegraDeNegocioException("Recomendação não encontrada para exclusão.");
        }
        recomendacaoRepository.deleteById(id);
    }


    // ------------------- MÉTODOS AUXILIARES -------------------

    private void validarCamposObrigatorios(Recomendacao r) {

        if (r.getPlantio() == null || r.getPlantio().getId() == null) {
            throw new RegraDeNegocioException("É obrigatório informar o plantio relacionado.");
        }

        if (r.getTipoAcao() == null) {
            throw new RegraDeNegocioException("O tipo da ação é obrigatório.");
        }

        if (r.getStatus() == null) {
            throw new RegraDeNegocioException("O status da recomendação é obrigatório.");
        }
    }

    private void validarDatas(Recomendacao r) {

        LocalDateTime geracao = converter(r.getDataGeracao());
        LocalDateTime conclusao = converter(r.getDataConclusao());

        if (geracao != null && geracao.isAfter(LocalDateTime.now())) {
            throw new RegraDeNegocioException("A data de geração não pode ser no futuro.");
        }

        if (conclusao != null && conclusao.isBefore(geracao)) {
            throw new RegraDeNegocioException("A data de conclusão não pode ser anterior à geração.");
        }

        // Se status for EXECUTADA_AUTOMATICA → precisa ter conclusão
        if (r.getStatus() == StatusRecomendacao.EXECUTADA_AUTOMATICA && conclusao == null) {
            throw new RegraDeNegocioException("Recomendações automáticas executadas devem ter a data de conclusão.");
        }

        // Se NÃO for executada, limpeza opcional
        if (r.getStatus() != StatusRecomendacao.EXECUTADA_AUTOMATICA) {
            r.setDataConclusao(null);
        }
    }

    private void validarValores(Recomendacao r) {
        // CORREÇÃO AQUI: Troque &lt;= por <=
        if (r.getQuantidade() != null && r.getQuantidade() <= 0) {
            throw new RegraDeNegocioException("A quantidade deve ser maior que zero.");
        }
    }

    private void carregarPlantio(Recomendacao r) {
        Plantio p = plantioRepository.findById(r.getPlantio().getId())
                .orElseThrow(() -> new RegraDeNegocioException("O plantio informado não existe."));
        r.setPlantio(p);
    }

    private LocalDateTime converter(Timestamp ts) {
        return ts != null ? ts.toLocalDateTime() : null;
    }
}