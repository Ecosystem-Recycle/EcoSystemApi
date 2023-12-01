package com.senai.apiecosystem.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record UsuarioDto(
        @NotBlank String nome,
        @NotBlank @Email(message = "O Email deve estar em formato válido") String email,
        @NotBlank String senha,
        String telefone,

        String genero,

        String cpf_Cnpj,

        String tipo_User,

        UUID endereco_id

) {

}
