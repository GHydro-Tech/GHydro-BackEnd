package com.ghydrobackend.ghydro.repository;

import com.ghydrobackend.ghydro.model.ConfiguracaoCusto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConfiguracaoCustoRepository extends JpaRepository<ConfiguracaoCusto, Long>{

}
