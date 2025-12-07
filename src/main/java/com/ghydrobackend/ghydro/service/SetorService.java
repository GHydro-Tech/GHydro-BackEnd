package com.ghydrobackend.ghydro.service;

import com.ghydrobackend.ghydro.exception.RegraDeNegocioException;
import com.ghydrobackend.ghydro.model.DispositivoIrrigacao;
import com.ghydrobackend.ghydro.model.Propriedade;
import com.ghydrobackend.ghydro.model.Setor;
import com.ghydrobackend.ghydro.model.TipoSolo;
import com.ghydrobackend.ghydro.repository.DispositivoIrrigacaoRepository;
import com.ghydrobackend.ghydro.repository.PropriedadeRepository;
import com.ghydrobackend.ghydro.repository.SetorRepository;
import com.ghydrobackend.ghydro.repository.TipoSoloRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SetorService {

    @Autowired
    private SetorRepository setorRepository;

    @Autowired
    private PropriedadeRepository propriedadeRepository;

    @Autowired
    private TipoSoloRepository tipoSoloRepository;

    @Autowired
    private DispositivoIrrigacaoRepository dispositivoIrrigacaoRepository;

    public List<Setor> listarSetor() {
        return setorRepository.findAll();
    }

    public Setor buscarPorId(Long id) {
        return setorRepository.findById(id)
                .orElseThrow(() -> new RegraDeNegocioException("Setor não encontrado com o ID: " + id));
    }

    @Transactional
    public Setor salvarSetor(Setor setor) {
        validarCamposObrigatorios(setor);

        // 1. Busca e valida as dependências (Isso resolve o problema de retorno nulo!)
        carregarDependencias(setor);

        // 2. Valida unicidade do nome DENTRO da propriedade
        if (setorRepository.existsByNomeAndPropriedadeId(setor.getNome(), setor.getPropriedade().getId())) {
            throw new RegraDeNegocioException("Já existe um setor chamado '" + setor.getNome() + "' nesta propriedade.");
        }

        return setorRepository.save(setor);
    }

    @Transactional
    public Setor atualizarSetor(Long id, Setor setorAtualizado) {
        Setor setorExistente = buscarPorId(id);

        validarCamposObrigatorios(setorAtualizado);

        // Se mudou alguma dependência, recarrega ela do banco
        // Isso garante que estamos vinculando a objetos reais e existentes
        if (setorAtualizado.getPropriedade() != null) {
            Propriedade prop = buscarPropriedade(setorAtualizado.getPropriedade().getId());
            setorExistente.setPropriedade(prop);
        }

        if (setorAtualizado.getTipoSolo() != null) {
            TipoSolo solo = buscarTipoSolo(setorAtualizado.getTipoSolo().getId());
            setorExistente.setTipoSolo(solo);
        }

        if (setorAtualizado.getDispositivoIrrigacao() != null) {
            DispositivoIrrigacao disp = buscarDispositivo(setorAtualizado.getDispositivoIrrigacao().getId());
            setorExistente.setDispositivoIrrigacao(disp);
        }

        // Atualiza campos simples
        setorExistente.setNome(setorAtualizado.getNome());
        setorExistente.setPoligonoGeografico(setorAtualizado.getPoligonoGeografico());

        // Valida duplicidade de nome (considerando a propriedade atual do setor)
        boolean nomeJaExiste = setorRepository.existsByNomeAndPropriedadeIdAndIdNot(
                setorExistente.getNome(),
                setorExistente.getPropriedade().getId(),
                id
        );

        if (nomeJaExiste) {
            throw new RegraDeNegocioException("Já existe outro setor com este nome nesta propriedade.");
        }

        return setorRepository.save(setorExistente);
    }

    public void deletarSetor(Long id) {
        if (!setorRepository.existsById(id)) {
            throw new RegraDeNegocioException("Setor não encontrado para exclusão.");
        }
        // Futuro: Validar se existem plantios ativos neste setor antes de deletar
        setorRepository.deleteById(id);
    }

    // --- Métodos Auxiliares de Validação e Carga ---

    private void validarCamposObrigatorios(Setor s) {
        if (s.getNome() == null || s.getNome().trim().isEmpty()) {
            throw new RegraDeNegocioException("O nome do setor é obrigatório.");
        }
        if (s.getPoligonoGeografico() == null || s.getPoligonoGeografico().trim().isEmpty()) {
            throw new RegraDeNegocioException("O polígono geográfico é obrigatório.");
        }
    }

    // Este metodo é o segredo para não retornar nulo e validar existência
    private void carregarDependencias(Setor setor) {
        if (setor.getPropriedade() == null || setor.getPropriedade().getId() == null) {
            throw new RegraDeNegocioException("A propriedade é obrigatória.");
        }
        setor.setPropriedade(buscarPropriedade(setor.getPropriedade().getId()));

        if (setor.getTipoSolo() == null || setor.getTipoSolo().getId() == null) {
            throw new RegraDeNegocioException("O tipo de solo é obrigatório.");
        }
        setor.setTipoSolo(buscarTipoSolo(setor.getTipoSolo().getId()));

        if (setor.getDispositivoIrrigacao() == null || setor.getDispositivoIrrigacao().getId() == null) {
            throw new RegraDeNegocioException("O dispositivo de irrigação é obrigatório.");
        }
        setor.setDispositivoIrrigacao(buscarDispositivo(setor.getDispositivoIrrigacao().getId()));
    }

    // Buscas individuais com erro personalizado
    private Propriedade buscarPropriedade(Long id) {
        return propriedadeRepository.findById(id)
                .orElseThrow(() -> new RegraDeNegocioException("Propriedade não encontrada (ID: " + id + ")"));
    }

    private TipoSolo buscarTipoSolo(Long id) {
        return tipoSoloRepository.findById(id)
                .orElseThrow(() -> new RegraDeNegocioException("Tipo de Solo não encontrado (ID: " + id + ")"));
    }

    private DispositivoIrrigacao buscarDispositivo(Long id) {
        return dispositivoIrrigacaoRepository.findById(id)
                .orElseThrow(() -> new RegraDeNegocioException("Dispositivo de Irrigação não encontrado (ID: " + id + ")"));
    }
}