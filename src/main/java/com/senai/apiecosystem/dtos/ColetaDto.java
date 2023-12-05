package com.senai.apiecosystem.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Date;
import java.util.UUID;

public record ColetaDto(
        @NotBlank String disponibilidade,
        @NotBlank Date data_cadastro,
        @NotNull UUID anuncio_id,
        @NotNull UUID tipo_status_id,
        @NotNull UUID usuario_id
) {

}
