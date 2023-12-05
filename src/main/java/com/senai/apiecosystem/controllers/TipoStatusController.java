package com.senai.apiecosystem.controllers;

import com.senai.apiecosystem.dtos.TipoStatusDto;
import com.senai.apiecosystem.dtos.TipoUsuarioDto;
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

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping(value = "/tipo_status", produces = {"application/json"})
public class TipoStatusController {
    @Autowired
    TipoStatusRepository tipoStatusRepository;

    @Operation(summary = "Método para CONSULTAR todos os possíveis tipos de status do sistema", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Dados retornados com sucesso")
    })
    @GetMapping
    public ResponseEntity<List<TipoStatusModel>> listarTipoStatus(){
        return ResponseEntity.status(HttpStatus.OK).body(tipoStatusRepository.findAll());
    }

    @Operation(summary = "Método para CADASTRAR um novo Tipo de Status", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Cadastro de Tipo de Status com Sucesso"),
            @ApiResponse(responseCode = "400", description = "Parâmetros inválidos")
    })
    @PostMapping
    public ResponseEntity<Object> cadastrarTipoStatus(@RequestBody @Valid TipoStatusDto tipoStatusDto) {
        TipoStatusModel TipoStatus = new TipoStatusModel();
        BeanUtils.copyProperties(tipoStatusDto, TipoStatus);

        return ResponseEntity.status(HttpStatus.CREATED).body(tipoStatusRepository.save(TipoStatus));
    }


}


