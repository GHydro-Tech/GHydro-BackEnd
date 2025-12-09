package com.ghydrobackend.ghydro.service;

import com.ghydrobackend.ghydro.dto.CadastroProprietarioDTO;
import com.ghydrobackend.ghydro.model.Proprietario;
import com.ghydrobackend.ghydro.model.Usuario;
import com.ghydrobackend.ghydro.repository.ProprietarioRepository;
import com.ghydrobackend.ghydro.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthService implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ProprietarioRepository proprietarioRepository;

    // Metodo obrigatório para o Login (Spring Security usa isso internamente)
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByLogin(username);
        if (usuario == null) {
            throw new UsernameNotFoundException("Usuário não encontrado!");
        }
        return usuario;
    }

    // --- SEU METODO DE CADASTRO ---
    @Transactional
    public void cadastrar(CadastroProprietarioDTO dados) {
        // 1. Verifica se o login (email) já existe
        if (usuarioRepository.findByLogin(dados.login()) != null) {
            throw new RuntimeException("Este login já está em uso.");
        }

        // 2. Criptografa a senha (Segurança)
        String senhaCriptografada = new BCryptPasswordEncoder().encode(dados.senha());

        // 3. Cria e Salva o Usuário (Acesso ao sistema)
        Usuario novoUsuario = new Usuario();
        novoUsuario.setLogin(dados.login());
        novoUsuario.setSenha(senhaCriptografada);

        // Salvamos primeiro para gerar o ID do usuário
        Usuario usuarioSalvo = usuarioRepository.save(novoUsuario);

        // 4. Cria e Salva o Proprietário (Dados do Negócio)
        Proprietario novoProprietario = new Proprietario();
        novoProprietario.setNome(dados.nome());
        novoProprietario.setCpf(dados.cpf());

        // AQUI É O VÍNCULO: Amarramos o proprietário ao usuário criado acima
        novoProprietario.setUsuario(usuarioSalvo);

        proprietarioRepository.save(novoProprietario);
    }
}