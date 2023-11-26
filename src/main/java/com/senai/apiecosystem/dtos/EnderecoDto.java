package com.senai.apiecosystem.dtos;

public record EnderecoDto(
        String logradouro,
        String numero,
        String bairro,
        String cidade,
        String estado,
        String cep

) {

}
