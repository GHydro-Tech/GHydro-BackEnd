package com.ghydrobackend.ghydro.service;

import com.ghydrobackend.ghydro.exception.RegraDeNegocioException;
import com.ghydrobackend.ghydro.model.DispositivoIrrigacao;
import com.ghydrobackend.ghydro.model.Setor;
import com.ghydrobackend.ghydro.repository.DispositivoIrrigacaoRepository;
import com.ghydrobackend.ghydro.repository.SetorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DispositivoIrrigacaoService {

    @Autowired
    private DispositivoIrrigacaoRepository dispositivoRepository;

    @Autowired
    private SetorRepository setorRepository;

    public List<DispositivoIrrigacao> listarDispositivoIrrigacao() {
        return dispositivoRepository.findAll();
    }

    public DispositivoIrrigacao buscarPorId(Long id) {
        return dispositivoRepository.findById(id)
                .orElseThrow(() -> new RegraDeNegocioException("Dispositivo não encontrado com o ID: " + id));
    }

    @Transactional
    public DispositivoIrrigacao salvarDispositivoIrrigacao(DispositivoIrrigacao dispositivo) {
        validarCamposObrigatorios(dispositivo);
        validarFisica(dispositivo);

        // Carrega o setor completo
        carregarSetor(dispositivo);

        // Valida unicidade de nome no setor
        if (dispositivoRepository.existsByNomeAndSetorId(dispositivo.getNome(), dispositivo.getSetor().getId())) {
            throw new RegraDeNegocioException("Já existe um dispositivo chamado '" + dispositivo.getNome() + "' neste setor.");
        }

        return dispositivoRepository.save(dispositivo);
    }

    @Transactional
    public DispositivoIrrigacao atualizarDispositivoIrrigacao(Long id, DispositivoIrrigacao dispositivoAtualizado) {
        DispositivoIrrigacao dispositivoExistente = buscarPorId(id);

        validarCamposObrigatorios(dispositivoAtualizado);
        validarFisica(dispositivoAtualizado);

        // Se mudou de setor
        if (dispositivoAtualizado.getSetor() != null) {
            carregarSetor(dispositivoAtualizado);
            dispositivoExistente.setSetor(dispositivoAtualizado.getSetor());
        }

        // Atualiza campos
        dispositivoExistente.setNome(dispositivoAtualizado.getNome());
        dispositivoExistente.setTipoDispositivo(dispositivoAtualizado.getTipoDispositivo());
        dispositivoExistente.setEficienciaIrrigacao(dispositivoAtualizado.getEficienciaIrrigacao());
        dispositivoExistente.setVazaoNominal(dispositivoAtualizado.getVazaoNominal());
        dispositivoExistente.setPotenciaMotor(dispositivoAtualizado.getPotenciaMotor());

        // Valida duplicidade de nome na atualização
        boolean nomeJaExiste = dispositivoRepository.existsByNomeAndSetorIdAndIdNot(
                dispositivoExistente.getNome(),
                dispositivoExistente.getSetor().getId(),
                id
        );

        if (nomeJaExiste) {
            throw new RegraDeNegocioException("Já existe outro dispositivo com este nome neste setor.");
        }

        return dispositivoRepository.save(dispositivoExistente);
    }

    public void deletarDispositivoIrrigacao(Long id) {
        if (!dispositivoRepository.existsById(id)) {
            throw new RegraDeNegocioException("Dispositivo não encontrado para exclusão.");
        }
        dispositivoRepository.deleteById(id);
    }

    // --- Métodos Auxiliares ---

    private void validarCamposObrigatorios(DispositivoIrrigacao d) {
        if (d.getNome() == null || d.getNome().trim().isEmpty()) {
            throw new RegraDeNegocioException("O nome do dispositivo é obrigatório.");
        }
        if (d.getTipoDispositivo() == null) {
            throw new RegraDeNegocioException("O tipo do dispositivo é obrigatório.");
        }
    }

    private void validarFisica(DispositivoIrrigacao d) {
        // Eficiência: 0 a 100%
        if (d.getEficienciaIrrigacao() != null) {
            if (d.getEficienciaIrrigacao() <= 0 || d.getEficienciaIrrigacao() > 100) {
                throw new RegraDeNegocioException("A eficiência deve ser maior que 0 e menor ou igual a 100.");
            }
        }

        // Vazão: Positiva
        if (d.getVazaoNominal() != null && d.getVazaoNominal() <= 0) {
            throw new RegraDeNegocioException("A vazão nominal deve ser maior que zero.");
        }

        // Potência: Positiva
        if (d.getPotenciaMotor() != null && d.getPotenciaMotor() <= 0) {
            throw new RegraDeNegocioException("A potência do motor deve ser maior que zero.");
        }
    }


    private void carregarSetor(DispositivoIrrigacao d) {
        if (d.getSetor() == null || d.getSetor().getId() == null) {
            throw new RegraDeNegocioException("É obrigatório vincular o dispositivo a um Setor.");
        }

        Setor setorCompleto = setorRepository.findById(d.getSetor().getId())
                .orElseThrow(() -> new RegraDeNegocioException("O Setor informado (ID " + d.getSetor().getId() + ") não existe."));

        d.setSetor(setorCompleto);
    }
}