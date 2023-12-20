package com.senai.apiecosystem.controllers;


import com.senai.apiecosystem.dtos.AnuncioDto;
import com.senai.apiecosystem.dtos.ProdutoDto;
import com.senai.apiecosystem.models.AnuncioModel;
import com.senai.apiecosystem.models.CategoriaModel;
import com.senai.apiecosystem.models.ProdutoModel;
import com.senai.apiecosystem.models.TipoStatusModel;
import com.senai.apiecosystem.repositories.AnuncioRepository;
import com.senai.apiecosystem.repositories.CategoriaRepository;
import com.senai.apiecosystem.repositories.ProdutoRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;



import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping(value = "/produto", produces = {"application/json"})
public class ProdutoController {
    @Autowired
    ProdutoRepository produtoRepository;
    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private AnuncioRepository anuncioRepository;

    @Operation(summary = "Método para CONSULTAR todos os produtos", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Dados retornados com sucesso")
    })
    @GetMapping
    public ResponseEntity<List<ProdutoModel>> listarProdutos(){
        return ResponseEntity.status(HttpStatus.OK).body(produtoRepository.findAll());
    }

    @Operation(summary = "Método para CONSULTAR um determinado produto especificando seu ID", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Dados retornados com sucesso"),
            @ApiResponse(responseCode = "404", description = "Produto não encontrado")
    })
    @GetMapping("/{idProduto}")
    public ResponseEntity<Object> exibirProduto(@PathVariable(value = "idProduto") UUID id) {
        Optional<ProdutoModel> produtoBuscado = produtoRepository.findById(id);

        if (produtoBuscado.isEmpty()) {
            // Retornar usuario não encontrado
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Produtp não encontrado");
        }

        return ResponseEntity.status(HttpStatus.OK).body(produtoBuscado.get());
    }
    @Operation(summary = "Método para CONSULTAR um determinado produto via ID do Anuncio", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Dados retornados com sucesso"),
            @ApiResponse(responseCode = "404", description = "Lista de Produtos não encontrado")
    })
    @GetMapping("anuncio/{idAnuncio}")
    public ResponseEntity<List<ProdutoModel>> exibirProdutoAnuncio(@PathVariable(value = "idAnuncio") UUID id) {
        var produtoBuscado = produtoRepository.findProdAnuncioById(id);

        if (produtoBuscado.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.status(HttpStatus.OK).body(produtoRepository.findProdAnuncioById(id));
    }

    @Operation(summary = "Método para CADASTRAR um novo produto", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Cadastro do Produto com Sucesso"),
            @ApiResponse(responseCode = "400", description = "Parâmetros inválidos")
    })
    @PostMapping
    public ResponseEntity<Object> cadastrarProduto(@RequestBody @Valid ProdutoDto produtoDto) {
        ProdutoModel produtoModel = new ProdutoModel();

        BeanUtils.copyProperties(produtoDto, produtoModel);

        Optional<CategoriaModel> categoria = categoriaRepository.findByNome(produtoDto.categoria());

        var anuncio = anuncioRepository.findById(produtoDto.anuncio_id());

        if (categoria.isPresent() && anuncio.isPresent()) {
            produtoModel.setCategoria(categoria.get());
            produtoModel.setAnuncio(anuncio.get());
        } else {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body("ID Categoria ou ID Anuncio não encontrado");
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(produtoRepository.save(produtoModel));
    }

    @Operation(summary = "Método para ALTERAR um determinado produto especificando seu ID", method = "PUT")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Alteração do produto com sucesso"),
            @ApiResponse(responseCode = "404", description = "produto não encontrado")
    })
    @PutMapping(value = "/{idProduto}")
    public ResponseEntity<Object> editarProduto(@PathVariable(value = "idProduto") UUID id, @RequestBody @Valid ProdutoDto produtoDto) {
        Optional<ProdutoModel> produtoBuscado = produtoRepository.findById(id);

        if (produtoBuscado.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Produto não encontrado");
        }

        ProdutoModel produto = produtoBuscado.get();
        BeanUtils.copyProperties(produtoDto, produto);

        return ResponseEntity.status(HttpStatus.CREATED).body(produtoRepository.save(produto));
    }

    @Operation(summary = "Método para DELETAR um determinado produto especificando seu ID", method = "DELETE")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Produto deletado com sucesso com sucesso"),
            @ApiResponse(responseCode = "404", description = "Produto não encontrado")
    })
    @DeleteMapping("/{idProduto}")
    public ResponseEntity<Object> deletarProduto(@PathVariable(value = "idProduto") UUID id) {
        Optional<ProdutoModel> produtoBuscado = produtoRepository.findById(id);

        if (produtoBuscado.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Produto não encontrado");
        }

        produtoRepository.delete(produtoBuscado.get());

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Produto deletado com sucesso!");
    }

}
