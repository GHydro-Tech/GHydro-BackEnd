package com.ghydrobackend.ghydro.controller;

import com.ghydrobackend.ghydro.model.Manutencao;
import com.ghydrobackend.ghydro.service.ManutencaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("admin/manutencao") // URL isolada para indicar que é área administrativa
@CrossOrigin("*")
@PreAuthorize("hasAnyRole('ADMIN', 'TECNICO')") // Tranca TODAS as rotas desta classe!
public class ManutencaoController {

    @Autowired
    private ManutencaoService manutencaoService;

    @PostMapping
    public Manutencao salvarManutencao(@RequestBody Manutencao manutencao) {
        return manutencaoService.salvarManutencao(manutencao);
    }

    @GetMapping
    public List<Manutencao> listarManutencoes() {
        return manutencaoService.listarManutencoes();
    }

    // Rota para ver o histórico de uma peça só: GET /admin/manutencao/equipamento/SENSOR/5
    @GetMapping("/equipamento/{tipo}/{id}")
    public List<Manutencao> buscarPorEquipamento(@PathVariable String tipo, @PathVariable Long id) {
        return manutencaoService.buscarPorEquipamento(id, tipo);
    }

    @PutMapping("/{id}")
    public Manutencao atualizarManutencao(@PathVariable Long id, @RequestBody Manutencao manutencao) {
        return manutencaoService.atualizarManutencao(id, manutencao);
    }

    @DeleteMapping("/{id}")
    public void deletarManutencao(@PathVariable Long id) {
        manutencaoService.deletarManutencao(id);
    }
}