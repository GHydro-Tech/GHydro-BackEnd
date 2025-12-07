package com.ghydrobackend.ghydro.service;

import com.ghydrobackend.ghydro.exception.RegraDeNegocioException;
import com.ghydrobackend.ghydro.model.Cultura;
import com.ghydrobackend.ghydro.repository.CulturaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CulturaService {

    @Autowired
    private CulturaRepository culturaRepository;

    public List<Cultura> listarCultura() {
        return culturaRepository.findAll();
    }

    public Cultura buscarPorId(Long id) {
        return culturaRepository.findById(id)
                .orElseThrow(() -> new RegraDeNegocioException("Cultura não encontrada com o ID: " + id));
    }

    @Transactional
    public Cultura salvarCultura(Cultura cultura) {
        // 1. Valida dados obrigatórios básicos
        validarCamposObrigatorios(cultura);

        // 2. Valida duplicidade (Regra de Negócio)
        if (culturaRepository.existsByNomePopularAndVariedade(cultura.getNomePopular(), cultura.getVariedade())) {
            throw new RegraDeNegocioException(
                    "Já existe uma cultura cadastrada com o nome '" + cultura.getNomePopular() +
                            "' e variedade '" + cultura.getVariedade() + "'."
            );
        }

        return culturaRepository.save(cultura);
    }

    @Transactional
    public Cultura atualizarCultura(Long id, Cultura culturaAtualizada) {
        // Garante que o ID existe antes de tentar atualizar
        Cultura culturaExistente = buscarPorId(id);

        validarCamposObrigatorios(culturaAtualizada);

        // Valida se a alteração vai gerar um conflito com OUTRO registro
        boolean jaExiste = culturaRepository.existsByNomePopularAndVariedadeAndIdNot(
                culturaAtualizada.getNomePopular(),
                culturaAtualizada.getVariedade(),
                id
        );

        if (jaExiste) {
            throw new RegraDeNegocioException("Já existe outra cultura com este nome e variedade.");
        }

        // Atualiza os dados do objeto recuperado do banco
        culturaExistente.setNomeCientifico(culturaAtualizada.getNomeCientifico());
        culturaExistente.setNomePopular(culturaAtualizada.getNomePopular());
        culturaExistente.setVariedade(culturaAtualizada.getVariedade());

        // Se precisar atualizar a lista de estados fenológicos, a lógica seria aqui

        return culturaRepository.save(culturaExistente);
    }

    public void deletarCultura(Long id) {
        if (!culturaRepository.existsById(id)) {
            throw new RegraDeNegocioException("Cultura não encontrada para exclusão.");
        }
        culturaRepository.deleteById(id);
    }

    // Metodo auxiliar para não poluir o código principal
    private void validarCamposObrigatorios(Cultura cultura) {
        if (cultura.getNomePopular() == null || cultura.getNomePopular().trim().isEmpty()) {
            throw new RegraDeNegocioException("O nome popular é obrigatório.");
        }
        if (cultura.getVariedade() == null || cultura.getVariedade().trim().isEmpty()) {
            throw new RegraDeNegocioException("A variedade é obrigatória.");
        }
    }
}