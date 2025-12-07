package com.ghydrobackend.ghydro.service;

import com.ghydrobackend.ghydro.exception.RegraDeNegocioException;
import com.ghydrobackend.ghydro.model.ConfiguracaoCusto;
import com.ghydrobackend.ghydro.model.Propriedade;
import com.ghydrobackend.ghydro.repository.ConfiguracaoCustoRepository;
import com.ghydrobackend.ghydro.repository.PropriedadeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ConfiguracaoCustoService {

    @Autowired
    private ConfiguracaoCustoRepository custoRepository;

    @Autowired
    private PropriedadeRepository propriedadeRepository;

    public List<ConfiguracaoCusto> listarConfiguracaoCusto() {
        return custoRepository.findAll();
    }

    public ConfiguracaoCusto buscarPorId(Long id) {
        return custoRepository.findById(id)
                .orElseThrow(() -> new RegraDeNegocioException("Configuração de custo não encontrada com o ID: " + id));
    }

    @Transactional
    public ConfiguracaoCusto salvarConfiguracaoCusto(ConfiguracaoCusto config) {
        validarCamposObrigatorios(config);
        validarValores(config);

        // 1. Carrega a Propriedade completa
        carregarPropriedade(config);

        // 2. REGRA DE OURO (1:1): Verifica se essa propriedade JÁ TEM configuração
        if (custoRepository.existsByPropriedadeId(config.getPropriedade().getId())) {
            throw new RegraDeNegocioException("Esta propriedade já possui uma configuração de custos definida. Use a atualização.");
        }

        return custoRepository.save(config);
    }

    @Transactional
    public ConfiguracaoCusto atualizarConfiguracaoCusto(Long id, ConfiguracaoCusto configAtualizada) {
        ConfiguracaoCusto existente = buscarPorId(id);

        validarCamposObrigatorios(configAtualizada);
        validarValores(configAtualizada);

        // Se tentar mudar a propriedade (raro, mas possível)
        if (configAtualizada.getPropriedade() != null) {
            carregarPropriedade(configAtualizada);

            // Verifica se a nova propriedade já tem dono (exceto se for a mesma config)
            boolean jaExiste = custoRepository.existsByPropriedadeIdAndIdNot(
                    configAtualizada.getPropriedade().getId(),
                    id
            );

            if (jaExiste) {
                throw new RegraDeNegocioException("A propriedade informada já possui outra configuração de custos.");
            }

            existente.setPropriedade(configAtualizada.getPropriedade());
        }

        // Atualiza valores
        existente.setCustoM3Agua(configAtualizada.getCustoM3Agua());
        existente.setCustoKWh(configAtualizada.getCustoKWh());
        existente.setMoeda(configAtualizada.getMoeda());

        return custoRepository.save(existente);
    }

    public void deletarConfiguracaoCusto(Long id) {
        if (!custoRepository.existsById(id)) {
            throw new RegraDeNegocioException("Configuração não encontrada para exclusão.");
        }
        custoRepository.deleteById(id);
    }

    // --- Métodos Auxiliares ---

    private void validarCamposObrigatorios(ConfiguracaoCusto c) {
        if (c.getMoeda() == null) {
            throw new RegraDeNegocioException("A moeda é obrigatória.");
        }
        if (c.getCustoM3Agua() == null) {
            throw new RegraDeNegocioException("O custo do m³ de água é obrigatório.");
        }
        if (c.getCustoKWh() == null) {
            throw new RegraDeNegocioException("O custo do kWh é obrigatório.");
        }
    }

    private void validarValores(ConfiguracaoCusto c) {
        if (c.getCustoM3Agua() < 0) {
            throw new RegraDeNegocioException("O custo da água não pode ser negativo.");
        }
        if (c.getCustoKWh() < 0) {
            throw new RegraDeNegocioException("O custo de energia não pode ser negativo.");
        }
    }

    private void carregarPropriedade(ConfiguracaoCusto c) {
        if (c.getPropriedade() == null || c.getPropriedade().getId() == null) {
            throw new RegraDeNegocioException("É obrigatório informar a Propriedade.");
        }

        Propriedade prop = propriedadeRepository.findById(c.getPropriedade().getId())
                .orElseThrow(() -> new RegraDeNegocioException("A Propriedade informada não existe."));

        c.setPropriedade(prop);
    }
}