package com.senai.apiecosystem.controllers;

import com.senai.apiecosystem.dtos.CategoriaDto;
import com.senai.apiecosystem.models.CategoriaModel;
import com.senai.apiecosystem.repositories.CategoriaRepository;
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
@RequestMapping(value = "/categoria", produces = {"application/json"})
public class CategoriaController {
    @Autowired
    CategoriaRepository categoriaRepository;

    @Operation(summary = "Método para CONSULTAR todas as possíveis categorias de produtos do sistema", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Dados retornados com sucesso")
    })
    @GetMapping
    public ResponseEntity<List<CategoriaModel>> listarCategorias(){
        return ResponseEntity.status(HttpStatus.OK).body(categoriaRepository.findAll());
    }

    @Operation(summary = "Método para CADASTRAR uma nova Categoria de produto", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Cadastro de Nova Categoria com Sucesso"),
            @ApiResponse(responseCode = "400", description = "Parâmetros inválidos")
    })
    @PostMapping
    public ResponseEntity<Object> cadastrarCategoria(@RequestBody @Valid CategoriaDto categoriaDto) {
        CategoriaModel CategoriaNew = new CategoriaModel();
        BeanUtils.copyProperties(categoriaDto, CategoriaNew);

        return ResponseEntity.status(HttpStatus.CREATED).body(categoriaRepository.save(CategoriaNew));
    }

}
