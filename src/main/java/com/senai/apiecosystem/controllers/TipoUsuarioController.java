package com.senai.apiecosystem.controllers;

import com.senai.apiecosystem.dtos.TipoUsuarioDto;
import com.senai.apiecosystem.models.TipoUsuarioModel;
import com.senai.apiecosystem.repositories.TipoUsuarioRepository;
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
@RequestMapping(value = "/tipo_usuarios", produces = {"application/json"})
public class TipoUsuarioController {
    @Autowired
    TipoUsuarioRepository tipoUsuarioRepository;

    @Operation(summary = "Método para CONSULTAR todos os possíveis tipos de usuários do sistema", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Dados retornados com sucesso")
    })
    @GetMapping
    public ResponseEntity<List<TipoUsuarioModel>> listarTipoUsuarios(){
        return ResponseEntity.status(HttpStatus.OK).body(tipoUsuarioRepository.findAll());
    }

    @Operation(summary = "Método para CADASTRAR um novo Tipo de Usuário", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Cadastro do Tipo de Usuário com Sucesso"),
            @ApiResponse(responseCode = "400", description = "Parâmetros inválidos")
    })
    @PostMapping
    public ResponseEntity<Object> cadastrarTipoUsuario(@RequestBody @Valid TipoUsuarioDto tipoUsuarioDto) {
        TipoUsuarioModel TipoUsuario = new TipoUsuarioModel();
        BeanUtils.copyProperties(tipoUsuarioDto, TipoUsuario);

        return ResponseEntity.status(HttpStatus.CREATED).body(tipoUsuarioRepository.save(TipoUsuario));
    }


}


