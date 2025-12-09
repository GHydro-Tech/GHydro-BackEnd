package com.ghydrobackend.ghydro.dto;

public record CadastroProprietarioDTO(
        String login, // email
        String senha,
        String nome,
        String cpf
) {
}