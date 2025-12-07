package com.ghydrobackend.ghydro.service;

import com.ghydrobackend.ghydro.exception.RegraDeNegocioException;
import com.ghydrobackend.ghydro.model.Propriedade;
import com.ghydrobackend.ghydro.model.Proprietario;
import com.ghydrobackend.ghydro.repository.PropriedadeRepository;
import com.ghydrobackend.ghydro.repository.ProprietarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PropriedadeService {

    @Autowired
    private PropriedadeRepository propriedadeRepository;

    @Autowired
    private ProprietarioRepository proprietarioRepository; // valida o dono

    public List<Propriedade> listarPropriedade() {
        return propriedadeRepository.findAll();
    }

    public Propriedade buscarPorId(Long id) {
        return propriedadeRepository.findById(id)
                .orElseThrow(() -> new RegraDeNegocioException("Propriedade não encontrada com o ID: " + id));
    }

    @Transactional
    public Propriedade salvarPropriedade(Propriedade propriedade) {
        validarCamposObrigatorios(propriedade);


        // BUSCAR o objeto completo para evitar retornar objeto nulo
        Long idProprietario = propriedade.getProprietario().getId();

        Proprietario proprietarioCompleto = proprietarioRepository.findById(idProprietario)
                .orElseThrow(() -> new RegraDeNegocioException("Proprietário não encontrado com o ID: " + idProprietario));

        // Injetamos o objeto completo dentro da propriedade
        propriedade.setProprietario(proprietarioCompleto);


        // Regra de duplicidade
        if (propriedadeRepository.existsByNomeAndProprietarioId(propriedade.getNome(), idProprietario)) {
            throw new RegraDeNegocioException("Este proprietário já possui uma propriedade chamada '" + propriedade.getNome() + "'.");
        }

        return propriedadeRepository.save(propriedade);
    }

    @Transactional
    public Propriedade atualizarPropriedade(Long id, Propriedade propriedadeAtualizada) {
        Propriedade propriedadeExistente = buscarPorId(id);

        validarCamposObrigatorios(propriedadeAtualizada);

        // Se estiver tentando trocar o proprietário na atualização, validamos se o novo existe
        if (propriedadeAtualizada.getProprietario() != null) {
            validarProprietario(propriedadeAtualizada);
            propriedadeExistente.setProprietario(propriedadeAtualizada.getProprietario());
        }

        // Validação de duplicidade de nome (considerando o dono atual ou o novo dono)
        Long idProprietario = propriedadeAtualizada.getProprietario() != null ?
                propriedadeAtualizada.getProprietario().getId() :
                propriedadeExistente.getProprietario().getId();

        boolean nomeJaExiste = propriedadeRepository.existsByNomeAndProprietarioIdAndIdNot(
                propriedadeAtualizada.getNome(),
                idProprietario,
                id
        );

        if (nomeJaExiste) {
            throw new RegraDeNegocioException("Este proprietário já possui outra propriedade com este nome.");
        }

        // Atualiza dados
        propriedadeExistente.setNome(propriedadeAtualizada.getNome());
        propriedadeExistente.setLocalizacao(propriedadeAtualizada.getLocalizacao());

        // Nota: Atualizar listas (setores/estações) ou ConfiguraçãoCusto geralmente
        // é feito em endpoints específicos ou requer lógica extra de merge.

        return propriedadeRepository.save(propriedadeExistente);
    }

    public void deletarPropriedade(Long id) {
        if (!propriedadeRepository.existsById(id)) {
            throw new RegraDeNegocioException("Propriedade não encontrada para exclusão.");
        }
        propriedadeRepository.deleteById(id);
    }

    // --- Métodos Auxiliares ---

    private void validarCamposObrigatorios(Propriedade p) {
        if (p.getNome() == null || p.getNome().trim().isEmpty()) {
            throw new RegraDeNegocioException("O nome da propriedade é obrigatório.");
        }
        if (p.getLocalizacao() == null || p.getLocalizacao().trim().isEmpty()) {
            throw new RegraDeNegocioException("A localização é obrigatória.");
        }
    }

    private void validarProprietario(Propriedade p) {
        if (p.getProprietario() == null || p.getProprietario().getId() == null) {
            throw new RegraDeNegocioException("É obrigatório informar o proprietário da propriedade.");
        }

        // Verifica se o ID do proprietário enviado realmente existe no banco
        boolean proprietarioExiste = proprietarioRepository.existsById(p.getProprietario().getId());
        if (!proprietarioExiste) {
            throw new RegraDeNegocioException("O proprietário informado (ID " + p.getProprietario().getId() + ") não existe.");
        }
    }
}