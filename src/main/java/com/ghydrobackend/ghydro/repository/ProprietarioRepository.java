package com.ghydrobackend.ghydro.repository;

import com.ghydrobackend.ghydro.model.Proprietario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProprietarioRepository extends JpaRepository<Proprietario, Long>{

    boolean existsByCpf(String cpf);
    boolean existsByLogin(String login);

    // Verificações para Atualização (UPDATE) - ignora o próprio ID
    boolean existsByCpfAndIdNot(String cpf, Long id);
    boolean existsByLoginAndIdNot(String login, Long id);
}
