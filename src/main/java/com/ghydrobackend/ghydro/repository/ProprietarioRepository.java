package com.ghydrobackend.ghydro.repository;

import com.ghydrobackend.ghydro.model.Proprietario;
import com.ghydrobackend.ghydro.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProprietarioRepository extends JpaRepository<Proprietario, Long>{

    boolean existsByCpf(String cpf);


    Optional<Proprietario> findByUsuario(Usuario usuario);


}
