package com.senai.apiecosystem.dtos;

import jakarta.validation.constraints.NotNull;

public record CategoriaDto(
        @NotNull String nome
) {

}
