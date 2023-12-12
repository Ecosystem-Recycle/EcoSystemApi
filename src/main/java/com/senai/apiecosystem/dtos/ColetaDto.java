package com.senai.apiecosystem.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record ColetaDto(

        @NotBlank String disponibilidade,

        @NotNull UUID usuario_id,

        @NotNull UUID anuncio_id,

        @NotBlank String tipo_status

) {

}
