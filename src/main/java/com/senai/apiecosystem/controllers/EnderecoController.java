package com.senai.apiecosystem.controllers;

import com.senai.apiecosystem.Repositories.EnderecoRepository;
import com.senai.apiecosystem.dtos.EnderecoDto;
import com.senai.apiecosystem.models.EnderecoModel;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.lang.model.util.ElementScanner14;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping(value = "/enderecos", produces = {"application/json"})
public class EnderecoController {
    @Autowired
    EnderecoRepository enderecoRepository;

    @Operation(summary = "Método para CONSULTAR todos enderecos", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Dados retornados com sucesso")
    })
    @GetMapping
    public ResponseEntity<List<EnderecoModel>> listarEnderecos(){
        return ResponseEntity.status(HttpStatus.OK).body(enderecoRepository.findAll());
    }

    @Operation(summary = "Método para CONSULTAR um determinado endereco especificando sua ID", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Dados retornados com sucesso"),
            @ApiResponse(responseCode = "404", description = "Usuario não encontrado")
    })
    @GetMapping("/{idEndereco}")
    public ResponseEntity<Object> exibirEndereco(@PathVariable(value = "idEndereco") UUID id) {
        Optional<EnderecoModel> enderecoBuscado = enderecoRepository.findById(id);
        if (enderecoBuscado.isEmpty()) {
            // Retornar Endereço não encontrado
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Endereço não encontrado");
        }

        return ResponseEntity.status(HttpStatus.OK).body(enderecoBuscado.get());
    }

    @Operation(summary = "Método para CRIAR um novo endereco", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Cadastro de Endereco com sucesso"),
            @ApiResponse(responseCode = "400", description = "Parâmetros inválidos")
    })
    @PostMapping
    public ResponseEntity<Object> cadastrarEndereco(@RequestBody @Valid EnderecoDto enderecoDto) {

        EnderecoModel enderecoModel = new EnderecoModel();

        BeanUtils.copyProperties(enderecoDto, enderecoModel);

        return ResponseEntity.status(HttpStatus.CREATED).body(enderecoRepository.save(enderecoModel));
    }

    @Operation(summary = "Método para ALTERAR um determinado endereco especificando sua ID", method = "PUT")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Alteração de endereco com sucesso"),
            @ApiResponse(responseCode = "404", description = "Endereco não encontrado")
    })
    @PutMapping(value = "/{idEndereco}")
    public ResponseEntity<Object> editarUsuario(@PathVariable(value = "idEndereco") UUID id, @RequestBody @Valid EnderecoDto enderecoDto) {
        Optional<EnderecoModel> enderecoBuscado = enderecoRepository.findById(id);

        if (enderecoBuscado.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Endereco não encontrado");
        }

        EnderecoModel endereco = enderecoBuscado.get();
        BeanUtils.copyProperties(enderecoRepository, endereco);

        return ResponseEntity.status(HttpStatus.CREATED).body(enderecoRepository.save(endereco));
    }

    @Operation(summary = "Método para DELETAR um determinado endereco especificando seu ID", method = "DELETE")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Endereco deletado com sucesso com sucesso"),
            @ApiResponse(responseCode = "404", description = "Endereco não encontrado")
    })
    @DeleteMapping("/{idEndereco}")
    public ResponseEntity<Object> deletarUsuario(@PathVariable(value = "idEndereco") UUID id) {
        Optional<EnderecoModel> enderecoBuscado = enderecoRepository.findById(id);

        if (enderecoBuscado.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Endereco não encontrado");
        }

        enderecoRepository.delete(enderecoBuscado.get());

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(" deletado com sucesso!");
    }

}