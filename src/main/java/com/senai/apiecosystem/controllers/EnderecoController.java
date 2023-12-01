package com.senai.apiecosystem.controllers;

import com.senai.apiecosystem.dtos.UsuarioDto;
import com.senai.apiecosystem.models.UsuarioModel;
import com.senai.apiecosystem.repositories.UsuarioRepository;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/usuarios", produces = {"application/json"})
public class UsuarioController {
    @Autowired
    UsuarioRepository usuarioRepository;

    @Operation(summary = "Método para CONSULTAR todos usuários", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Dados retornados com sucesso")
    })
    @GetMapping
    public ResponseEntity<List<UsuarioModel>> listarUsuarios(){
        return ResponseEntity.status(HttpStatus.OK).body(usuarioRepository.findAll());
    }

    @Operation(summary = "Método para CONSULTAR um determinado usuário especificando sua ID", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Dados retornados com sucesso"),
            @ApiResponse(responseCode = "404", description = "Usuario não encontrado")
    })

    @GetMapping("/{idUsuario}")
    public ResponseEntity<Object> exibirUsuario(@PathVariable(value = "idUsuario") UUID id) {
        Optional<UsuarioModel> usuarioBuscado = usuarioRepository.findById(id);

        if (usuarioBuscado.isEmpty()) {
            // Retornar usuario não encontrado
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado");
        }

        return ResponseEntity.status(HttpStatus.OK).body(usuarioBuscado.get());
    }

    @Operation(summary = "Método para CRIAR um novo usuário", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Cadastro de Usuário com sucesso"),
            @ApiResponse(responseCode = "400", description = "Parâmetros inválidos")
    })
    @PostMapping
    public ResponseEntity<Object> cadastrarUsuario(@RequestBody @Valid UsuarioDto usuarioDto) {

        UsuarioModel usuarioModel = new UsuarioModel();

        BeanUtils.copyProperties(usuarioDto, usuarioModel);

        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioRepository.save(usuarioModel));
    }

    @Operation(summary = "Método para ALTERAR um determinado usuário especificando sua ID", method = "PUT")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Alteração de usuário com sucesso"),
            @ApiResponse(responseCode = "404", description = "Usuario não encontrado")
    })
    @PutMapping(value = "/{idUsuario}")
    public ResponseEntity<Object> editarUsuario(@PathVariable(value = "idUsuario") UUID id, @RequestBody @Valid UsuarioDto usuarioDto) {
        Optional<UsuarioModel> usuarioBuscado = usuarioRepository.findById(id);

        if (usuarioBuscado.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario não encontrado");
        }

        UsuarioModel usuario = usuarioBuscado.get();
        BeanUtils.copyProperties(usuarioDto, usuario);

        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioRepository.save(usuario));
    }

    @Operation(summary = "Método para DELETAR um determinado usuário especificando seu ID", method = "DELETE")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Usuário deletado com sucesso com sucesso"),
            @ApiResponse(responseCode = "404", description = "Usuario não encontrado")
    })
    @DeleteMapping("/{idUsuario}")
    public ResponseEntity<Object> deletarUsuario(@PathVariable(value = "idUsuario") UUID id) {
        Optional<UsuarioModel> usuarioBuscado = usuarioRepository.findById(id);

        if (usuarioBuscado.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario não encontrado");
        }

        usuarioRepository.delete(usuarioBuscado.get());

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Usuario deletado com sucesso!");
    }

}