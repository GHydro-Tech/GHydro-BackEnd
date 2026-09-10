package com.ghydrobackend.ghydro.service;

import com.ghydrobackend.ghydro.model.Propriedade;
import com.ghydrobackend.ghydro.model.Setor;
import com.ghydrobackend.ghydro.model.TipoSolo;
import com.ghydrobackend.ghydro.model.Usuario;
import com.ghydrobackend.ghydro.model.enums.UserRole;
import com.ghydrobackend.ghydro.repository.PropriedadeRepository;
import com.ghydrobackend.ghydro.repository.SetorRepository;
import com.ghydrobackend.ghydro.repository.TipoSoloRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Service
public class SetorService {

    @Autowired
    private SetorRepository setorRepository;

    @Autowired
    private PropriedadeRepository propriedadeRepository;

    @Autowired
    private TipoSoloRepository tipoSoloRepository;

    public List<Setor> listarSetor() {
        Usuario usuarioLogado = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (usuarioLogado.getRole() == UserRole.PRODUTOR) {
            if (usuarioLogado.getProprietario() == null) {
                return Collections.emptyList();
            }
            // Navega Setor -> Propriedade -> Proprietario
            return setorRepository.findAllByPropriedadeProprietario(usuarioLogado.getProprietario());
        }

        return setorRepository.findAll();
    }

    public Setor buscarPorId(Long id) {
        Setor setor = setorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Setor não encontrado com o ID: " + id));

        Usuario usuarioLogado = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (usuarioLogado.getRole() == UserRole.PRODUTOR) {
            Long idDonoDoSetor = setor.getPropriedade().getProprietario().getId();
            
            if (usuarioLogado.getProprietario() == null || 
                !idDonoDoSetor.equals(usuarioLogado.getProprietario().getId())) {
                
                throw new AccessDeniedException("Acesso negado. Este setor pertence a outra fazenda.");
            }
        }

        return setor;
    }

    @Transactional
    public Setor salvarSetor(Setor setor) {
        validarCamposObrigatorios(setor);

        // 1. Carrega as dependências (agora apenas Propriedade e TipoSolo)
        carregarDependencias(setor);

        // 2. Valida unicidade do nome DENTRO da propriedade
        if (setorRepository.existsByNomeAndPropriedadeId(setor.getNome(), setor.getPropriedade().getId())) {
            throw new IllegalArgumentException("Já existe um setor chamado '" + setor.getNome() + "' nesta propriedade.");
        }

        return setorRepository.save(setor);
    }

    @Transactional
    public Setor atualizarSetor(Long id, Setor setorAtualizado) {
        Setor setorExistente = buscarPorId(id);

        validarCamposObrigatorios(setorAtualizado);

        // Se mudou a Propriedade
        if (setorAtualizado.getPropriedade() != null) {
            Propriedade prop = buscarPropriedade(setorAtualizado.getPropriedade().getId());
            setorExistente.setPropriedade(prop);
        }

        // Se mudou o Solo
        if (setorAtualizado.getTipoSolo() != null) {
            TipoSolo solo = buscarTipoSolo(setorAtualizado.getTipoSolo().getId());
            setorExistente.setTipoSolo(solo);
        }

        // Atualiza campos simples
        setorExistente.setNome(setorAtualizado.getNome());
        setorExistente.setPoligonoGeografico(setorAtualizado.getPoligonoGeografico());

        // Valida duplicidade de nome
        boolean nomeJaExiste = setorRepository.existsByNomeAndPropriedadeIdAndIdNot(
                setorExistente.getNome(),
                setorExistente.getPropriedade().getId(),
                id
        );

        if (nomeJaExiste) {
            throw new IllegalArgumentException("Já existe outro setor com este nome nesta propriedade.");
        }

        return setorRepository.save(setorExistente);
    }

    public void deletarSetor(Long id) {
        if (!setorRepository.existsById(id)) {
            throw new EntityNotFoundException("Setor não encontrado para exclusão.");
        }
        setorRepository.deleteById(id);
    }

    // --- Métodos Auxiliares ---

    private void validarCamposObrigatorios(Setor s) {
        if (s.getNome() == null || s.getNome().trim().isEmpty()) {
            throw new IllegalArgumentException("O nome do setor é obrigatório.");
        }
        if (s.getPoligonoGeografico() == null || s.getPoligonoGeografico().trim().isEmpty()) {
            throw new IllegalArgumentException("O polígono geográfico é obrigatório.");
        }
    }

    private void carregarDependencias(Setor setor) {
        // Propriedade continua Obrigatória
        if (setor.getPropriedade() == null || setor.getPropriedade().getId() == null) {
            throw new IllegalArgumentException("A propriedade é obrigatória.");
        }
        setor.setPropriedade(buscarPropriedade(setor.getPropriedade().getId()));

        // Solo continua Obrigatório
        if (setor.getTipoSolo() == null || setor.getTipoSolo().getId() == null) {
            throw new IllegalArgumentException("O tipo de solo é obrigatório.");
        }
        setor.setTipoSolo(buscarTipoSolo(setor.getTipoSolo().getId()));
    }

    private Propriedade buscarPropriedade(Long id) {
        return propriedadeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Propriedade não encontrada (ID: " + id + ")"));
    }

    private TipoSolo buscarTipoSolo(Long id) {
        return tipoSoloRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Tipo de Solo não encontrado (ID: " + id + ")"));
    }
}