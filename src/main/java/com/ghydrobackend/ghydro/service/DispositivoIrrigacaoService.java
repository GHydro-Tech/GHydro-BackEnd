package com.ghydrobackend.ghydro.service;



import com.ghydrobackend.ghydro.model.DispositivoIrrigacao;
import com.ghydrobackend.ghydro.repository.DispositivoIrrigacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DispositivoIrrigacaoService {

    @Autowired
    private DispositivoIrrigacaoRepository dispositivoIrrigacaoRepository;

    public DispositivoIrrigacao buscarPorId(Long id) {
        return dispositivoIrrigacaoRepository.findById(id).orElseThrow(() -> new RuntimeException("DispositivoIrrigacao não encontrado com o ID: " + id));
    }

    public DispositivoIrrigacao salvarDispositivoIrrigacao(DispositivoIrrigacao dispositivoIrrigacao) {
        return dispositivoIrrigacaoRepository.save(dispositivoIrrigacao);
    }

    public List<DispositivoIrrigacao> listarDispositivoIrrigacao() {
        return dispositivoIrrigacaoRepository.findAll();
    }

    public DispositivoIrrigacao atualizarDispositivoIrrigacao(DispositivoIrrigacao dispositivoIrrigacao) {
        return dispositivoIrrigacaoRepository.save(dispositivoIrrigacao);
    }

    public void deletarDispositivoIrrigacao(Long id) {
        dispositivoIrrigacaoRepository.deleteById(id);
    }
}
