package com.ghydrobackend.ghydro.service;

import com.ghydrobackend.ghydro.exception.RegraDeNegocioException;
import com.ghydrobackend.ghydro.model.EstacaoMetereologica;
import com.ghydrobackend.ghydro.model.LeituraClimatica;
import com.ghydrobackend.ghydro.repository.EstacaoMetereologicaRepository;
import com.ghydrobackend.ghydro.repository.LeituraClimaticaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class LeituraClimaticaService {

    @Autowired
    private LeituraClimaticaRepository leituraRepository;

    @Autowired
    private EstacaoMetereologicaRepository estacaoRepository;

    public List<LeituraClimatica> listarLeituraClimatica() {
        return leituraRepository.findAll();
    }

    public LeituraClimatica buscarPorId(Long id) {
        return leituraRepository.findById(id)
                .orElseThrow(() -> new RegraDeNegocioException("Leitura climática não encontrada com o ID: " + id));
    }

    @Transactional
    public LeituraClimatica salvarLeituraClimatica(LeituraClimatica leitura) {
        validarCamposObrigatorios(leitura);
        validarFisica(leitura);

        // 1. Carrega a Estação completa
        carregarEstacao(leitura);

        // 2. Evita duplicidade de horário para a mesma estação
        if (leituraRepository.existsByEstacaoMetereologicaIdAndDataHora(
                leitura.getEstacaoMetereologica().getId(),
                leitura.getDataHora())) {
            throw new RegraDeNegocioException("Já existe uma leitura registrada para esta estação neste horário.");
        }

        return leituraRepository.save(leitura);
    }

    @Transactional
    public LeituraClimatica atualizarLeituraClimatica(Long id, LeituraClimatica leituraAtualizada) {
        LeituraClimatica existente = buscarPorId(id);

        validarCamposObrigatorios(leituraAtualizada);
        validarFisica(leituraAtualizada);

        // Se mudou a estação
        if (leituraAtualizada.getEstacaoMetereologica() != null) {
            carregarEstacao(leituraAtualizada);
            existente.setEstacaoMetereologica(leituraAtualizada.getEstacaoMetereologica());
        }

        // Atualiza dados
        existente.setDataHora(leituraAtualizada.getDataHora());
        existente.setTemperaturaMaxima(leituraAtualizada.getTemperaturaMaxima());
        existente.setTemperaturaMinima(leituraAtualizada.getTemperaturaMinima());
        existente.setUmidadeRelativaAr(leituraAtualizada.getUmidadeRelativaAr());
        existente.setVelocidadeVento(leituraAtualizada.getVelocidadeVento());
        existente.setRadiacaoSolar(leituraAtualizada.getRadiacaoSolar());
        existente.setPrecipitacao(leituraAtualizada.getPrecipitacao());
        existente.setEtoCalculado(leituraAtualizada.getEtoCalculado());

        return leituraRepository.save(existente);
    }

    public void deletarLeituraClimatica(Long id) {
        if (!leituraRepository.existsById(id)) {
            throw new RegraDeNegocioException("Leitura não encontrada para exclusão.");
        }
        leituraRepository.deleteById(id);
    }

    // --- Métodos Auxiliares ---

    private void validarCamposObrigatorios(LeituraClimatica l) {
        if (l.getDataHora() == null) {
            throw new RegraDeNegocioException("A data e hora da leitura são obrigatórias.");
        }
        if (l.getDataHora().isAfter(LocalDateTime.now())) {
            throw new RegraDeNegocioException("A data da leitura não pode ser no futuro.");
        }
    }

    private void validarFisica(LeituraClimatica l) {
        // Validação Térmica
        if (l.getTemperaturaMaxima() != null && l.getTemperaturaMinima() != null) {
            if (l.getTemperaturaMinima() > l.getTemperaturaMaxima()) {
                throw new RegraDeNegocioException("A temperatura mínima não pode ser maior que a máxima.");
            }
        }

        // Umidade (0-100%)
        if (l.getUmidadeRelativaAr() != null) {
            if (l.getUmidadeRelativaAr() < 0 || l.getUmidadeRelativaAr() > 100) {
                throw new RegraDeNegocioException("A umidade relativa deve estar entre 0% e 100%.");
            }
        }

        // Valores não negativos
        if (l.getVelocidadeVento() != null && l.getVelocidadeVento() < 0) {
            throw new RegraDeNegocioException("A velocidade do vento não pode ser negativa.");
        }
        if (l.getRadiacaoSolar() != null && l.getRadiacaoSolar() < 0) {
            throw new RegraDeNegocioException("A radiação solar não pode ser negativa.");
        }
        if (l.getPrecipitacao() != null && l.getPrecipitacao() < 0) {
            throw new RegraDeNegocioException("A precipitação não pode ser negativa.");
        }
    }

    private void carregarEstacao(LeituraClimatica l) {
        if (l.getEstacaoMetereologica() == null || l.getEstacaoMetereologica().getId() == null) {
            throw new RegraDeNegocioException("É obrigatório informar a Estação Meteorológica.");
        }

        EstacaoMetereologica estacao = estacaoRepository.findById(l.getEstacaoMetereologica().getId())
                .orElseThrow(() -> new RegraDeNegocioException("A Estação informada não existe."));

        l.setEstacaoMetereologica(estacao);
    }
}