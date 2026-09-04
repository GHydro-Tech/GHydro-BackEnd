package com.ghydrobackend.ghydro.service;

import com.ghydrobackend.ghydro.model.Proprietario;
import com.ghydrobackend.ghydro.repository.ProprietarioRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProprietarioService {

    @Autowired
    private ProprietarioRepository proprietarioRepository;

    public List<Proprietario> listarProprietario() {
        return proprietarioRepository.findAll();
    }

    public Proprietario buscarPorId(Long id) {
        return proprietarioRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Proprietário não encontrado com o ID: " + id));
    }

    @Transactional
    public Proprietario salvarProprietario(Proprietario proprietario) {
        validarCamposObrigatorios(proprietario);

        // 1. Limpa o CPF (remove pontos e traços) para padronizar no banco
        String cpfLimpo = limparCpf(proprietario.getCpf());
        proprietario.setCpf(cpfLimpo);

        // 2. Valida duplicidade de CPF
        if (proprietarioRepository.existsByCpf(cpfLimpo)) {
            throw new IllegalArgumentException("Já existe um proprietário cadastrado com este CPF.");
        }

        return proprietarioRepository.save(proprietario);
    }

    @Transactional
    public Proprietario atualizarProprietario(Long id, Proprietario proprietarioAtualizado) {
        Proprietario proprietarioExistente = buscarPorId(id);

        validarCamposObrigatorios(proprietarioAtualizado);

        // Limpa o CPF novo
        String cpfLimpo = limparCpf(proprietarioAtualizado.getCpf());
        proprietarioAtualizado.setCpf(cpfLimpo);


        // Atualiza os dados
        proprietarioExistente.setNome(proprietarioAtualizado.getNome());
        proprietarioExistente.setCpf(cpfLimpo);

        return proprietarioRepository.save(proprietarioExistente);
    }

    public void deletarProprietario(Long id) {
        if (!proprietarioRepository.existsById(id)) {
            throw new EntityNotFoundException("Proprietário não encontrado para exclusão.");
        }
        proprietarioRepository.deleteById(id);
    }

    // --- Métodos Auxiliares ---

    private void validarCamposObrigatorios(Proprietario p) {
        if (p.getNome() == null || p.getNome().trim().isEmpty()) {
            throw new IllegalArgumentException("O nome é obrigatório.");
        }
        if (p.getCpf() == null || p.getCpf().trim().isEmpty()) {
            throw new IllegalArgumentException("O CPF é obrigatório.");
        }
    }

    private String limparCpf(String cpf) {
        // Remove tudo que não for número
        if (cpf == null) return null;
        return cpf.replaceAll("\\D", "");
    }
}