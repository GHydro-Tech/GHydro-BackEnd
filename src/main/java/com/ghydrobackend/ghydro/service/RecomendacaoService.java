package com.ghydrobackend.ghydro.service;

import com.ghydrobackend.ghydro.model.Recomendacao;
import com.ghydrobackend.ghydro.repository.RecomendacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecomendacaoService {

    @Autowired
    private RecomendacaoRepository recomendacaoRepository;

    public Recomendacao buscarPorId(Long id) {
        return recomendacaoRepository.findById(id).orElseThrow(() -> new RuntimeException("Recomendação não encontrado com o ID: " + id));
    }

    public Recomendacao salvarRecomendacao(Recomendacao recomendacao) {
        return recomendacaoRepository.save(recomendacao);
    }

    public List<Recomendacao> listarRecomendacao() {
        return recomendacaoRepository.findAll();
    }

    public Recomendacao atualizarRecomendacao(Recomendacao recomendacao) {
        return recomendacaoRepository.save(recomendacao);
    }

    public void deletarRecomendacao(Long id) {
        recomendacaoRepository.deleteById(id);
    }
}
