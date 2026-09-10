package com.ghydrobackend.ghydro.controller;

import com.ghydrobackend.ghydro.dto.AuthenticationDTO;
import com.ghydrobackend.ghydro.dto.LoginResponseDTO;
import com.ghydrobackend.ghydro.dto.RegisterDTO;
import com.ghydrobackend.ghydro.model.Usuario;
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
        // Verifica se já existe alguém com esse email
        if (this.repository.findByEmail(data.email()) != null) {
            return ResponseEntity.badRequest().body("Este email já está em uso.");
        }

        // Criptografa a senha antes de salvar no banco
        String encryptedPassword = new BCryptPasswordEncoder().encode(data.senha());
        
        // Cria o usuário novo (null para o ID, que será gerado, e null para o Proprietário, por enquanto)
        Usuario newUser = new Usuario(null, data.email(), encryptedPassword, data.role(), null);
        
        this.repository.save(newUser);

        return ResponseEntity.ok("Usuário cadastrado com sucesso!");
    }
}