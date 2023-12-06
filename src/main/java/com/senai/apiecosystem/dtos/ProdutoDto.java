package com.senai.apiecosystem.dtos;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record ProdutoDto(
        @NotNull String nome,
        Integer quantidade,
        String categoria,
        @NotNull UUID anuncio_id
) {

}
