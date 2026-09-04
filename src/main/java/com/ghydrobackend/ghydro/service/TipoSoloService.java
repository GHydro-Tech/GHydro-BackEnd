package com.ghydrobackend.ghydro.service;

import com.ghydrobackend.ghydro.model.TipoSolo;
import com.ghydrobackend.ghydro.repository.TipoSoloRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TipoSoloService {

    @Autowired
    private TipoSoloRepository tipoSoloRepository;

    public List<TipoSolo> listarTipoSolo() {
        return tipoSoloRepository.findAll();
    }

    public TipoSolo buscarPorId(Long id) {
        return tipoSoloRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Tipo de Solo não encontrado com o ID: " + id));
    }

    @Transactional
    public TipoSolo salvarTipoSolo(TipoSolo solo) {
        validarCamposObrigatorios(solo);
        validarFisicaSolo(solo);

        // Valida duplicidade de nome
        if (tipoSoloRepository.existsByDescricao(solo.getDescricao())) {
            throw new IllegalArgumentException("Já existe um Tipo de Solo cadastrado com esta descrição.");
        }

        return tipoSoloRepository.save(solo);
    }

    @Transactional
    public TipoSolo atualizarTipoSolo(Long id, TipoSolo soloAtualizado) {
        TipoSolo existente = buscarPorId(id);

        validarCamposObrigatorios(soloAtualizado);
        validarFisicaSolo(soloAtualizado);

        // Atualiza dados
        existente.setDescricao(soloAtualizado.getDescricao());
        existente.setCapacidadeCampo(soloAtualizado.getCapacidadeCampo());
        existente.setPontoMurcha(soloAtualizado.getPontoMurcha());
        existente.setDensidadeAparente(soloAtualizado.getDensidadeAparente());
        existente.setTaxaInfiltracaoBasica(soloAtualizado.getTaxaInfiltracaoBasica());

        // Valida duplicidade na atualização
        boolean nomeJaExiste = tipoSoloRepository.existsByDescricaoAndIdNot(
                existente.getDescricao(),
                id
        );

        if (nomeJaExiste) {
            throw new IllegalArgumentException("Já existe outro Tipo de Solo com esta descrição.");
        }

        return tipoSoloRepository.save(existente);
    }

    public void deletarTipoSolo(Long id) {
        if (!tipoSoloRepository.existsById(id)) {
            throw new EntityNotFoundException("Tipo de Solo não encontrado para exclusão.");
        }
        // Futuro: Validar se existem Setores usando este solo antes de deletar
        tipoSoloRepository.deleteById(id);
    }

    // --- Métodos Auxiliares ---

    private void validarCamposObrigatorios(TipoSolo s) {
        if (s.getDescricao() == null || s.getDescricao().trim().isEmpty()) {
            throw new IllegalArgumentException("A descrição do solo é obrigatória.");
        }
        if (s.getCapacidadeCampo() == null) {
            throw new IllegalArgumentException("A capacidade de campo é obrigatória.");
        }
        if (s.getPontoMurcha() == null) {
            throw new IllegalArgumentException("O ponto de murcha é obrigatório.");
        }
    }

    private void validarFisicaSolo(TipoSolo s) {
        // Valores positivos
        if (s.getCapacidadeCampo() <= 0 || s.getPontoMurcha() <= 0) {
            throw new IllegalArgumentException("Capacidade de campo e Ponto de murcha devem ser maiores que zero.");
        }

        // Regra Agronômica: CC > PM
        if (s.getPontoMurcha() >= s.getCapacidadeCampo()) {
            throw new IllegalArgumentException("A Capacidade de Campo deve ser maior que o Ponto de Murcha.");
        }

        // Densidade e Infiltração positivas
        if (s.getDensidadeAparente() != null && s.getDensidadeAparente() <= 0) {
            throw new IllegalArgumentException("A densidade aparente deve ser maior que zero.");
        }
        if (s.getTaxaInfiltracaoBasica() != null && s.getTaxaInfiltracaoBasica() <= 0) {
            throw new IllegalArgumentException("A taxa de infiltração deve ser maior que zero.");
        }
    }
}