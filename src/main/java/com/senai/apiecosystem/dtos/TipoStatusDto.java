package com.senai.apiecosystem.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record TipoStatusDto(
        @NotBlank String nome


) {

}