package com.ghydrobackend.ghydro.repository;

import com.ghydrobackend.ghydro.model.EstacaoMetereologica;
import com.ghydrobackend.ghydro.model.Proprietario;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EstacaoMetereologicaRepository extends JpaRepository<EstacaoMetereologica, Long>{

    List<EstacaoMetereologica> findAllByPropriedadesProprietario(Proprietario proprietario);
    
}
