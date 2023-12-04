package com.senai.apiecosystem.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record EnderecoDto(
        @NotBlank String id,

        @NotBlank String logradouro,

        String numero,

        String bairro,

        String cidade,

        String estado,
        String cep

) {

}