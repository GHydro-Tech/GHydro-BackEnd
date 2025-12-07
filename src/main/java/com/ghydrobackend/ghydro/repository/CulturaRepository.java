package com.ghydrobackend.ghydro.repository;

import com.ghydrobackend.ghydro.model.Cultura;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CulturaRepository extends JpaRepository<Cultura, Long>{

    boolean existsByNomePopularAndVariedade(String nomePopular, String variedade);

    boolean existsByNomePopularAndVariedadeAndIdNot(String nomePopular, String variedade, Long id);
}
