package com.ghydrobackend.ghydro.repository;

import com.ghydrobackend.ghydro.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    
    // Método que o Spring Security vai usar para buscar o usuário pelo email
    UserDetails findByEmail(String email);
}