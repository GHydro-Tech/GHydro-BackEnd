package com.ghydrobackend.ghydro.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import com.ghydrobackend.ghydro.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    // O Spring Security usa isso para achar o usuário no Login
    Usuario findByLogin(String login);
}