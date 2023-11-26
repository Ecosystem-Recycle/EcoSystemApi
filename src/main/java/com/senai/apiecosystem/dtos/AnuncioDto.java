package com.senai.apiecosystem.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.UUID;

public record AnuncioDto(
        @NotBlank String titulo,
        @NotBlank String disponibilidade,
        @NotBlank String periodo,
        MultipartFile imagem,
        @NotNull UUID usuario_id,
        @NotNull UUID tipo_status_id

) {
}
