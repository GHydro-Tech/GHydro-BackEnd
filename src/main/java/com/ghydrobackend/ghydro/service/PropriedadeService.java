package com.ghydrobackend.ghydro.service;

import com.ghydrobackend.ghydro.model.Propriedade;
import com.ghydrobackend.ghydro.model.Proprietario;
import com.ghydrobackend.ghydro.model.Usuario;
import com.ghydrobackend.ghydro.model.enums.UserRole;
import com.ghydrobackend.ghydro.repository.PropriedadeRepository;
import com.ghydrobackend.ghydro.repository.ProprietarioRepository;
import jakarta.persistence.EntityNotFoundException; // Use javax.persistence.* se estiver no Spring Boot 2.x
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Service
public class PropriedadeService {

    @Autowired
    private PropriedadeRepository propriedadeRepository;

    @Autowired
    private ProprietarioRepository proprietarioRepository;


    public List<Propriedade> listarPropriedade() {
        // 1. Pega o usuário que está logado no momento
        Usuario usuarioLogado = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        // 2. Verifica se a role é PRODUTOR
        if (usuarioLogado.getRole() == UserRole.PRODUTOR) {
            // Se for produtor, mas ainda não tem uma entidade Proprietario vinculada a ele, retorna lista vazia
            if (usuarioLogado.getProprietario() == null) {
                return Collections.emptyList();
            }
            // Retorna apenas as propriedades dele usando o método que já existe no seu repositório
            return propriedadeRepository.findAllByProprietario(usuarioLogado.getProprietario());
        }

        // 3. Se for ADMIN ou TECNICO, retorna tudo
        return propriedadeRepository.findAll();
    }

    public Propriedade buscarPorId(Long id) {
        // 1. Busca a propriedade normalmente no banco
        Propriedade propriedade = propriedadeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Propriedade não encontrada com o ID: " + id));

        // 2. Pega o usuário logado
        Usuario usuarioLogado = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        // 3. Verifica as permissões se for PRODUTOR
        if (usuarioLogado.getRole() == UserRole.PRODUTOR) {
            
            // Se o usuário logado ainda não tem um Proprietario vinculado a ele, ou se o ID for diferente
            if (usuarioLogado.getProprietario() == null || 
                !propriedade.getProprietario().getId().equals(usuarioLogado.getProprietario().getId())) {
                
                // Dispara um erro 403 Forbidden
                throw new AccessDeniedException("Acesso negado. Você não tem permissão para acessar esta propriedade.");
            }
        }

        return propriedade;
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