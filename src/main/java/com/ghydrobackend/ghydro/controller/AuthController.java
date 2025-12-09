package com.ghydrobackend.ghydro.controller;

import com.ghydrobackend.ghydro.dto.CadastroProprietarioDTO;
import com.ghydrobackend.ghydro.dto.LoginDTO;
import com.ghydrobackend.ghydro.dto.TokenJWTDTO;
import com.ghydrobackend.ghydro.model.Usuario;
import com.ghydrobackend.ghydro.service.AuthService;
import com.ghydrobackend.ghydro.service.TokenService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager manager; // Spring injeta isso

    @Autowired
    private TokenService tokenService; // Vamos criar essa classe no próximo passo rápido

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity efetuarLogin(@RequestBody @Valid LoginDTO dados) {
        var authenticationToken = new UsernamePasswordAuthenticationToken(dados.login(), dados.senha());
        var authentication = manager.authenticate(authenticationToken);

        var tokenJWT = tokenService.gerarToken((Usuario) authentication.getPrincipal());

        return ResponseEntity.ok(new TokenJWTDTO(tokenJWT));
    }

    @PostMapping("/cadastro")
    public ResponseEntity cadastrar(@RequestBody @Valid CadastroProprietarioDTO dados) {
        authService.cadastrar(dados);
        return ResponseEntity.status(201).build();
    }
}