package com.ghydrobackend.ghydro.security;

import com.ghydrobackend.ghydro.repository.UsuarioRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        var token = this.recoverToken(request);
        
        if (token != null) {
            // Se tem token, vamos descriptografar e pegar o email
            var email = tokenService.validarToken(token);
            
            // Busca o usuário no banco de dados
            UserDetails usuario = usuarioRepository.findByEmail(email);

            if (usuario != null) {
                // Monta o objeto de autenticação e avisa o Spring Security que esse usuário está autenticado
                var authentication = new UsernamePasswordAuthenticationToken(usuario, null, usuario.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        
        // Passa a requisição para frente (para os controllers ou para ser barrada)
        filterChain.doFilter(request, response);
    }

    private String recoverToken(HttpServletRequest request) {
        var authHeader = request.getHeader("Authorization");
        if (authHeader == null) return null;
        
        // O token padrão vem escrito "Bearer eyJhbGciOiJIUzI1NiIsInR..." 
        // Aqui nós removemos a palavra "Bearer " para ficar só com o código
        return authHeader.replace("Bearer ", "");
    }
}