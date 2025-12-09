package com.ghydrobackend.ghydro.service;

import com.ghydrobackend.ghydro.exception.RegraDeNegocioException;
import com.ghydrobackend.ghydro.model.Propriedade;
import com.ghydrobackend.ghydro.model.Proprietario;
import com.ghydrobackend.ghydro.model.Usuario;
import com.ghydrobackend.ghydro.repository.PropriedadeRepository;
import com.ghydrobackend.ghydro.repository.ProprietarioRepository;
import com.ghydrobackend.ghydro.repository.UsuarioRepository;
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

    @Autowired
    private UsuarioRepository usuarioRepository;

    public List<Propriedade> listarPropriedade() {
        return propriedadeRepository.findAll();
    }

    public Propriedade buscarPorId(Long id) {
        return propriedadeRepository.findById(id)
                .orElseThrow(() -> new RegraDeNegocioException("Propriedade não encontrada com o ID: " + id));
    }

    //
    // METODO NOVO: Usado pelo App Mobile logo após o cadastro/login
    // Ele descobre quem é o dono baseado no Token (login) e salva a fazenda pra ele.
    //
    @Transactional
    public Propriedade salvarPropriedadeViaToken(Propriedade propriedade, String loginUsuario) {
        // 1. Valida campos básicos (Nome, Localização)
        validarCamposObrigatorios(propriedade);

        // 2. Busca o Usuário pelo Login
        Usuario usuario = usuarioRepository.findByLogin(loginUsuario);
        if (usuario == null) {
            throw new RegraDeNegocioException("Usuário não encontrado na base de dados (Login: " + loginUsuario + ").");
        }

        // 3. Busca o Proprietário vinculado a esse Usuário
        // Nota: Se o seu repositório retorna Optional, use .orElse(null). Se retorna objeto direto, deixe assim.
        // Aqui estou assumindo que seu repositório retorna o Objeto direto ou Optional tratado.
        // O código abaixo funciona para ambos os casos se ajustado levemente,
        // mas vou seguir a lógica de que findByUsuario retorna o objeto Proprietario (pode ser null).
        Proprietario proprietario = proprietarioRepository.findByUsuario(usuario)
                .orElseThrow(() -> new RegraDeNegocioException("Nenhum perfil de proprietário encontrado para este usuário."));

        // 4. Vincula a propriedade ao proprietário encontrado
        propriedade.setProprietario(proprietario);

        // 5. Validação de duplicidade (Regra de Negócio)
        if (propriedadeRepository.existsByNomeAndProprietarioId(propriedade.getNome(), proprietario.getId())) {
            throw new RegraDeNegocioException("Você já possui uma propriedade chamada '" + propriedade.getNome() + "'.");
        }

        return propriedadeRepository.save(propriedade);
    }

    //
    // MÉTODOS ANTIGOS (Mantidos para compatibilidade ou uso Admin)
    //

    @Transactional
    public Propriedade salvarPropriedade(Propriedade propriedade) {
        validarCamposObrigatorios(propriedade);

        // Valida se o proprietário foi enviado manualmente no JSON
        if (propriedade.getProprietario() == null || propriedade.getProprietario().getId() == null) {
            throw new RegraDeNegocioException("É obrigatório informar o proprietário da propriedade.");
        }

        Long idProprietario = propriedade.getProprietario().getId();

        Proprietario proprietarioCompleto = proprietarioRepository.findById(idProprietario)
                .orElseThrow(() -> new RegraDeNegocioException("Proprietário não encontrado com o ID: " + idProprietario));

        propriedade.setProprietario(proprietarioCompleto);

        if (propriedadeRepository.existsByNomeAndProprietarioId(propriedade.getNome(), idProprietario)) {
            throw new RegraDeNegocioException("Este proprietário já possui uma propriedade chamada '" + propriedade.getNome() + "'.");
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
            throw new RegraDeNegocioException("Este proprietário já possui outra propriedade com este nome.");
        }

        propriedadeExistente.setNome(propriedadeAtualizada.getNome());
        propriedadeExistente.setLocalizacao(propriedadeAtualizada.getLocalizacao());

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
        if (!proprietarioRepository.existsById(p.getProprietario().getId())) {
            throw new RegraDeNegocioException("O proprietário informado não existe.");
        }
    }
}