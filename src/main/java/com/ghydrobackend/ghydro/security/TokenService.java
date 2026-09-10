package com.ghydrobackend.ghydro.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.ghydrobackend.ghydro.model.Usuario;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    // A chave secreta será puxada do seu application.properties
    @Value("${api.security.token.secret}")
    private String secret;

    public String gerarToken(Usuario usuario) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.create()
                    .withIssuer("ghydro-api")
                    .withSubject(usuario.getEmail())
                    // Colocamos a role dentro do token para facilitar no front-end!
                    .withClaim("role", usuario.getRole().getRole()) 
                    .withExpiresAt(genExpirationDate())
                    .sign(algorithm);
        } catch (JWTCreationException exception) {
            throw new RuntimeException("Erro ao gerar token jwt", exception);
        }
    }

    public String validarToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.require(algorithm)
                    .withIssuer("ghydro-api")
                    .build()
                    .verify(token)
                    .getSubject(); // Retorna o email se o token for válido
        } catch (JWTVerificationException exception) {
            return ""; // Retorna string vazia se o token for inválido ou estiver expirado
        }
    }

    private Instant genExpirationDate() {
        // O token expira em 2 horas. 
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }
}