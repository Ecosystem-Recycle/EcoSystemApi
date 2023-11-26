package com.senai.apiecosystem.controllers;

import com.senai.apiecosystem.dtos.EnderecoDto;
import com.senai.apiecosystem.models.EnderecoModel;
import com.senai.apiecosystem.repositories.EnderecoRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping(value = "/endereco", produces = {"application/json"})
public class EnderecoController {
    @Autowired
    EnderecoRepository enderecoRepository;

    @Operation(summary = "Método para CONSULTAR todos os endereços", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Dados retornados com sucesso")
    })
    @GetMapping
    public ResponseEntity<List<EnderecoModel>> listarEnderecos(){
        return ResponseEntity.status(HttpStatus.OK).body(enderecoRepository.findAll());
    }

    @Operation(summary = "Método para CONSULTAR um determinado endereço especificando seu ID", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Dados retornados com sucesso"),
            @ApiResponse(responseCode = "404", description = "Endereço não encontrado")
    })
    @GetMapping("/{idEndereco}")
    public ResponseEntity<Object> exibirEndereco(@PathVariable(value = "idEndereco") UUID id) {
        Optional<EnderecoModel> enderecoBuscado = enderecoRepository.findById(id);

        if (enderecoBuscado.isEmpty()) {
            // Retornar usuario não encontrado
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Endereço não encontrado");
        }

        return ResponseEntity.status(HttpStatus.OK).body(enderecoBuscado.get());
    }

    @Operation(summary = "Método para CADASTRAR um novo endereço", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Cadastro de Endereço com Sucesso"),
            @ApiResponse(responseCode = "400", description = "Parâmetros inválidos")
    })
    @PostMapping
    public ResponseEntity<Object> cadastrarEndereco(@RequestBody @Valid EnderecoDto enderecoDto) {
        EnderecoModel Endereco = new EnderecoModel();
        BeanUtils.copyProperties(enderecoDto, Endereco);

        return ResponseEntity.status(HttpStatus.CREATED).body(enderecoRepository.save(Endereco));
    }

    @Operation(summary = "Método para ALTERAR um determinado endereco especificando seu ID", method = "PUT")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Alteração de endereço com sucesso"),
            @ApiResponse(responseCode = "404", description = "endereço não encontrado")
    })
    @PutMapping(value = "/{idEndereco}")
    public ResponseEntity<Object> editarEndereco(@PathVariable(value = "idEndereco") UUID id, @RequestBody @Valid EnderecoDto enderecoDto) {
        Optional<EnderecoModel> enderecoBuscado = enderecoRepository.findById(id);

        if (enderecoBuscado.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Endereço não encontrado");
        }

        EnderecoModel endereco = enderecoBuscado.get();
        BeanUtils.copyProperties(enderecoDto, endereco);

        return ResponseEntity.status(HttpStatus.CREATED).body(enderecoRepository.save(endereco));
    }

    @Operation(summary = "Método para DELETAR um determinado Endereço especificando seu ID", method = "DELETE")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Endereço deletado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Endereço não encontrado")
    })
    @DeleteMapping("/{idEndereco}")
    public ResponseEntity<Object> deletarEndereco(@PathVariable(value = "idEndereco") UUID id) {
        Optional<EnderecoModel> enderecoBuscado = enderecoRepository.findById(id);

        if (enderecoBuscado.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Endereço não encontrado");
        }

        enderecoRepository.delete(enderecoBuscado.get());

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Endereço deletado com sucesso!");
    }

}
