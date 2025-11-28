package com.ghydrobackend.ghydro.service;

import com.ghydrobackend.ghydro.model.ConfiguracaoCusto;
import com.ghydrobackend.ghydro.repository.ConfiguracaoCustoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConfiguracaoCustoService {

    @Autowired
    private ConfiguracaoCustoRepository configuracaoCustoRepository;

    public ConfiguracaoCusto buscarPorId(Long id) {
        return configuracaoCustoRepository.findById(id).orElseThrow(() -> new RuntimeException("Configuracao do Custo não encontrado com o ID: " + id));
    }

    public ConfiguracaoCusto salvarConfiguracaoCusto(ConfiguracaoCusto configuracaoCusto) {
        return configuracaoCustoRepository.save(configuracaoCusto);
    }

    public List<ConfiguracaoCusto> listarConfiguracaoCusto() {
        return configuracaoCustoRepository.findAll();
    }

    public ConfiguracaoCusto atualizarConfiguracaoCusto(ConfiguracaoCusto configuracaoCusto) {
        return configuracaoCustoRepository.save(configuracaoCusto);
    }

    public void deletarConfiguracaoCusto(Long id) {
        configuracaoCustoRepository.deleteById(id);
    }
}
