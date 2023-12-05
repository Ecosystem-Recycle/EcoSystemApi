package com.senai.apiecosystem.controllers;

import com.senai.apiecosystem.dtos.ColetaDto;
import com.senai.apiecosystem.models.ColetaModel;
import com.senai.apiecosystem.repositories.ColetaRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.Getter;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping(value = "/coletas", produces = {"application/json"})
public class ColetaController {
    @Autowired
    ColetaRepository coletaRepository;

    @Operation(summary = "Método para CONSULTAR todas as coletas", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Dados retornados com sucesso")
    })
    @GetMapping
    public ResponseEntity<List<ColetaModel>> listarColetas(){
        return ResponseEntity.status(HttpStatus.OK).body(coletaRepository.findAll());
    }

    @Operation(summary = "Método para CONSULTAR uma determinada coleta especificando sua ID", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Dados retornados com sucesso"),
            @ApiResponse(responseCode = "404", description = "Coleta não encontrada")
    })

    @GetMapping("/{idColeta}")
    public ResponseEntity<Object> exibirColeta(@PathVariable(value = "idColeta") UUID id) {
        Optional<ColetaModel> coletaBuscada = coletaRepository.findById(id);

        if (coletaBuscada.isPresent()) {
            // Retornar coleta não encontrada
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Coleta não encontrada");
        }

        return ResponseEntity.status(HttpStatus.OK).body(coletaBuscada.get());
    }

    @Operation(summary = "Método para CRIAR uma nova coleta", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Cadastro da coleta feita com sucesso"),
            @ApiResponse(responseCode = "400", description = "Parâmetros inválidos")
    })
    @PostMapping
    public ResponseEntity<Object> cadastrarColeta(@RequestBody @Valid ColetaDto coletaDto) {

        ColetaModel coletaModel = new ColetaModel();

        BeanUtils.copyProperties(coletaDto, coletaModel);

        return ResponseEntity.status(HttpStatus.CREATED).body(coletaRepository.save(coletaModel));
    }

    @Operation(summary = "Método para ALTERAR uma determinada coleta especificando sua ID", method = "PUT")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Alteração de coleta feita com sucesso"),
            @ApiResponse(responseCode = "404", description = "Coleta não encontrada")
    })
    @PutMapping(value = "/{idColeta}")
    public ResponseEntity<Object> editarColeta(@PathVariable(value = "idColeta") UUID id, @RequestBody @Valid ColetaDto coletaDto) {
        Optional<ColetaModel> coletaBuscada = coletaRepository.findById(id);

        if (coletaBuscada.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Coleta não encontrada");
        }

        ColetaModel coleta = coletaBuscada.get();
        BeanUtils.copyProperties(coletaDto, coleta);

        return ResponseEntity.status(HttpStatus.CREATED).body(coletaRepository.save(coleta));
    }

    @Operation(summary = "Método para DELETAR uma determinada coleta especificando seu ID", method = "DELETE")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Coleta deletada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Coleta não encontrada")
    })
    @DeleteMapping("/{idColeta}")
    public ResponseEntity<Object> deletarColeta(@PathVariable(value = "idColeta") UUID id) {
        Optional<ColetaModel> coletaBuscada = coletaRepository.findById(id);

        if (coletaBuscada.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Coleta não encontrada");
        }

        coletaRepository.delete(coletaBuscada.get());

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Coleta deletada com sucesso!");
    }

}
