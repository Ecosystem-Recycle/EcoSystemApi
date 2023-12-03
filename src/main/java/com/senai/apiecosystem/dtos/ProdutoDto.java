package com.senai.apiecosystem.dtos;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record ProdutoDto(
        @NotNull String nome,
        Integer quantidade,
        @NotNull UUID categoria_id,
        @NotNull UUID anuncio_id
) {

}