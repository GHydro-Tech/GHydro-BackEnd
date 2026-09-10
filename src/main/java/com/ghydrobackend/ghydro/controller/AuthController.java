package com.ghydrobackend.ghydro.controller;

import com.ghydrobackend.ghydro.dto.AuthenticationDTO;
import com.ghydrobackend.ghydro.dto.LoginResponseDTO;
import com.ghydrobackend.ghydro.dto.RegisterDTO;
import com.ghydrobackend.ghydro.model.Proprietario;
import com.ghydrobackend.ghydro.model.Usuario;
import com.ghydrobackend.ghydro.repository.ProprietarioRepository;
import com.ghydrobackend.ghydro.repository.UsuarioRepository;
import com.ghydrobackend.ghydro.security.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("auth")
@CrossOrigin("*")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UsuarioRepository repository;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private ProprietarioRepository proprietarioRepository;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody AuthenticationDTO data) {
        // O Spring Security exige que a gente encapsule o email e a senha nesse token interno dele
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.email(), data.senha());
        
        // Aqui o Spring vai lá no AutenticacaoService, busca o usuário e compara a senha criptografada
        var auth = this.authenticationManager.authenticate(usernamePassword);

        // Se a senha bater, geramos o token JWT
        var token = tokenService.gerarToken((Usuario) auth.getPrincipal());

        return ResponseEntity.ok(new LoginResponseDTO(token));
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody RegisterDTO data) {
        if (this.repository.findByEmail(data.email()) != null) {
            return ResponseEntity.badRequest().body("Este email já está em uso.");
        }

        String encryptedPassword = new BCryptPasswordEncoder().encode(data.senha());
        Proprietario proprietarioVinculado = null;

        // Se for PRODUTOR, cria a entidade do arquivo Proprietario.java
        if (data.role() == com.ghydrobackend.ghydro.model.enums.UserRole.PRODUTOR) {
            Proprietario novoProprietario = new Proprietario();
            
            // Como não há obrigatoriedade, podemos colocar um nome padrão
            // para não quebrar telas no Front-end que esperam uma String
            novoProprietario.setNome("Produtor (Em Configuração)");
            // novoProprietario.setCpf(null); // O CPF pode ficar nulo por enquanto
            
            proprietarioVinculado = this.proprietarioRepository.save(novoProprietario);
        }
        
        Usuario newUser = new Usuario(null, data.email(), encryptedPassword, data.role(), proprietarioVinculado);
        this.repository.save(newUser);

        return ResponseEntity.ok("Usuário cadastrado com sucesso!");
    }
}