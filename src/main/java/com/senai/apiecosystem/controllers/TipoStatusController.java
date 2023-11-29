package com.senai.apiecosystem.controllers;

import com.senai.apiecosystem.dtos.TipoStatusDto;
import com.senai.apiecosystem.models.TipoStatusModel;
import com.senai.apiecosystem.repositories.TipoStatusRepository;
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
@RequestMapping(value = "/status", produces = {"application/json"})
public class TipoStatusController {
    @Autowired
    TipoStatusRepository tipoStatusRepository;

    @Operation(summary = "Método para CONSULTAR todos status", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Dados retornados com sucesso")
    })
    @GetMapping
    public ResponseEntity<List<TipoStatusModel>> listarStatus(){
        return ResponseEntity.status(HttpStatus.OK).body(tipoStatusRepository.findAll());
    }

    @Operation(summary = "Método para CONSULTAR um determinado Status especificando sua ID", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Dados retornados com sucesso"),
            @ApiResponse(responseCode = "404", description = "Status não encontrado")
    })

    @GetMapping("/{idStatus}")
    public ResponseEntity<Object> exibirStatus(@PathVariable(value = "idStatus") UUID id) {
        Optional<TipoStatusModel> statusBuscado = tipoStatusRepository.findById(id);

        if (statusBuscado.isEmpty()) {
            // Retornar usuario não encontrado
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado");
        }

        return ResponseEntity.status(HttpStatus.OK).body(statusBuscado.get());
    }

    @Operation(summary = "Método para CRIAR um novo status", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Cadastro de status com sucesso"),
            @ApiResponse(responseCode = "400", description = "Parâmetros inválidos")
    })
    @PostMapping
    public ResponseEntity<Object> cadastrarStatus(@RequestBody @Valid TipoStatusDto statusDto) {

        TipoStatusModel statusModel = new TipoStatusModel();

        BeanUtils.copyProperties(statusDto, statusModel);

        return ResponseEntity.status(HttpStatus.CREATED).body(tipoStatusRepository.save(statusModel));
    }

    @Operation(summary = "Método para ALTERAR um determinado status especificando sua ID", method = "PUT")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Alteração de status com sucesso"),
            @ApiResponse(responseCode = "404", description = "Status não encontrado")
    })
    @PutMapping(value = "/{idStatus}")
    public ResponseEntity<Object> editarUsuario(@PathVariable(value = "idStatus") UUID id, @RequestBody @Valid TipoStatusDto statusDto) {
        Optional<TipoStatusModel> statusBuscado = tipoStatusRepository.findById(id);

        if (statusBuscado.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Status não encontrado");
        }

        TipoStatusModel status = statusBuscado.get();
        BeanUtils.copyProperties(statusDto, status);

        return ResponseEntity.status(HttpStatus.CREATED).body(tipoStatusRepository.save(status));
    }

    @Operation(summary = "Método para DELETAR um determinado status especificando seu ID", method = "DELETE")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "status deletado com sucesso com sucesso"),
            @ApiResponse(responseCode = "404", description = "status não encontrado")
    })
    @DeleteMapping("/{idStatus}")
    public ResponseEntity<Object> deletarUsuario(@PathVariable(value = "idStatus") UUID id) {
        Optional<TipoStatusModel> statusBuscado = tipoStatusRepository.findById(id);

        if (statusBuscado.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Status não encontrado");
        }

        tipoStatusRepository.delete(statusBuscado.get());

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Status deletado com sucesso!");
    }

}