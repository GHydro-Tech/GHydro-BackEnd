package com.ghydrobackend.ghydro.service;

import com.ghydrobackend.ghydro.model.Propriedade;
import com.ghydrobackend.ghydro.model.Proprietario;
import com.ghydrobackend.ghydro.repository.PropriedadeRepository;
import com.ghydrobackend.ghydro.repository.ProprietarioRepository;
import jakarta.persistence.EntityNotFoundException; // Use javax.persistence.* se estiver no Spring Boot 2.x
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PropriedadeService {

    @Autowired
    private PropriedadeRepository propriedadeRepository;

    @Autowired
    private ProprietarioRepository proprietarioRepository;


    public List<Propriedade> listarPropriedade() {
        return propriedadeRepository.findAll();
    }

    public Propriedade buscarPorId(Long id) {
        return propriedadeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Propriedade não encontrada com o ID: " + id));
    }

    @Transactional
    public Propriedade salvarPropriedade(Propriedade propriedade) {
        validarCamposObrigatorios(propriedade);

        // Valida se o proprietário foi enviado manualmente no JSON
        if (propriedade.getProprietario() == null || propriedade.getProprietario().getId() == null) {
            throw new IllegalArgumentException("É obrigatório informar o proprietário da propriedade.");
        }

        Long idProprietario = propriedade.getProprietario().getId();

        Proprietario proprietarioCompleto = proprietarioRepository.findById(idProprietario)
                .orElseThrow(() -> new EntityNotFoundException("Proprietário não encontrado com o ID: " + idProprietario));

        propriedade.setProprietario(proprietarioCompleto);

        if (propriedadeRepository.existsByNomeAndProprietarioId(propriedade.getNome(), idProprietario)) {
            throw new IllegalArgumentException("Este proprietário já possui uma propriedade chamada '" + propriedade.getNome() + "'.");
        }

        return propriedadeRepository.save(propriedade);
    }

    @Transactional
    public Propriedade atualizarPropriedade(Long id, Propriedade propriedadeAtualizada) {
        Propriedade propriedadeExistente = buscarPorId(id);

        validarCamposObrigatorios(propriedadeAtualizada);

        // Se estiver tentando trocar o proprietário na atualização
        if (propriedadeAtualizada.getProprietario() != null) {
            validarProprietario(propriedadeAtualizada);
            propriedadeExistente.setProprietario(propriedadeAtualizada.getProprietario());
        }

        // Validação de duplicidade de nome
        Long idProprietario = (propriedadeAtualizada.getProprietario() != null) ?
                propriedadeAtualizada.getProprietario().getId() :
                propriedadeExistente.getProprietario().getId();

        boolean nomeJaExiste = propriedadeRepository.existsByNomeAndProprietarioIdAndIdNot(
                propriedadeAtualizada.getNome(),
                idProprietario,
                id
        );

        if (nomeJaExiste) {
            throw new IllegalArgumentException("Este proprietário já possui outra propriedade com este nome.");
        }

        propriedadeExistente.setNome(propriedadeAtualizada.getNome());
        propriedadeExistente.setLocalizacao(propriedadeAtualizada.getLocalizacao());

        return propriedadeRepository.save(propriedadeExistente);
    }

    public void deletarPropriedade(Long id) {
        if (!propriedadeRepository.existsById(id)) {
            throw new EntityNotFoundException("Propriedade não encontrada para exclusão.");
        }
        propriedadeRepository.deleteById(id);
    }

    // --- Métodos Auxiliares ---

    private void validarCamposObrigatorios(Propriedade p) {
        if (p.getNome() == null || p.getNome().trim().isEmpty()) {
            throw new IllegalArgumentException("O nome da propriedade é obrigatório.");
        }
        if (p.getLocalizacao() == null || p.getLocalizacao().trim().isEmpty()) {
            throw new IllegalArgumentException("A localização é obrigatória.");
        }
    }

    private void validarProprietario(Propriedade p) {
        if (p.getProprietario() == null || p.getProprietario().getId() == null) {
            throw new IllegalArgumentException("É obrigatório informar o proprietário da propriedade.");
        }
        if (!proprietarioRepository.existsById(p.getProprietario().getId())) {
            throw new EntityNotFoundException("O proprietário informado não existe.");
        }
    }
}