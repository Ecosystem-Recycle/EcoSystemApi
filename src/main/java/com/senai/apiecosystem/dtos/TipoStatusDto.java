package com.senai.apiecosystem.dtos;

import jakarta.validation.constraints.NotNull;

public record TipoStatusDto(
        @NotNull String nome
) {

}
