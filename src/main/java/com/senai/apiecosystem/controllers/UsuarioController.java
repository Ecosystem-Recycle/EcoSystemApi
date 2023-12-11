package com.senai.apiecosystem.controllers;


import com.senai.apiecosystem.dtos.UsuarioDto;
import com.senai.apiecosystem.models.EnderecoModel;
import com.senai.apiecosystem.models.TipoUsuarioModel;
import com.senai.apiecosystem.models.UsuarioModel;
import com.senai.apiecosystem.repositories.EnderecoRepository;
import com.senai.apiecosystem.repositories.TipoUsuarioRepository;
import com.senai.apiecosystem.repositories.UsuarioRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping(value = "/usuarios", produces = {"application/json"})
public class UsuarioController {
    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    private TipoUsuarioRepository tipoUsuarioRepository;

    @Autowired
    private EnderecoRepository enderecoRepository;

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

    @Operation(summary = "Método para CONSULTAR um determinado usuário especificando seu email", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Dados retornados com sucesso"),
            @ApiResponse(responseCode = "404", description = "Usuario não encontrado")
    })
    @GetMapping("/email/{emailUsuario}")
    public ResponseEntity<Object> exibirUsuarioEmail(@PathVariable(value = "emailUsuario") String email) {
        Optional<UserDetails> usuarioBuscado = Optional.ofNullable(usuarioRepository.findByEmail(email));
//        UserDetails usuario = usuarioRepository.findByEmail(email);

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
        if (usuarioRepository.findByEmail(usuarioDto.email()) != null) {
            // Não pode cadastrar
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Esse email já está cadastrado!");
        }

        UsuarioModel usuarioNovo = new UsuarioModel();

        BeanUtils.copyProperties(usuarioDto, usuarioNovo);

        Optional<TipoUsuarioModel> tipoUsuario = tipoUsuarioRepository.findByNome(usuarioDto.tipo_User());

        if (tipoUsuario.isPresent()) {
            usuarioNovo.setTipousuario(tipoUsuario.get());
        }else{
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body("ID do Tipo Usuario não encontrado");
        }
        Optional<EnderecoModel> endereco = enderecoRepository.findById(usuarioDto.endereco_id());

        if (endereco.isPresent()) {
            usuarioNovo.setEndereco(endereco.get());
        } else {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body("id_Endereço não encontrado");
        }



        String senhaCriptografada = new BCryptPasswordEncoder().encode(usuarioDto.senha());
        usuarioNovo.setSenha(senhaCriptografada);

        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioRepository.save(usuarioNovo));
    }

    @Operation(summary = "Método para CRIAR um novo usuário como MEDIAPART", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Cadastro de Usuário com sucesso"),
            @ApiResponse(responseCode = "400", description = "Parâmetros inválidos")
    })
    @PostMapping(value = "/login", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<Object> cadastrarUsuarioLogin(@ModelAttribute @Valid UsuarioDto usuarioDto) {
        if (usuarioRepository.findByEmail(usuarioDto.email()) != null) {
            // Não pode cadastrar
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Esse email já está cadastrado!");
        }

        UsuarioModel usuarioNovo = new UsuarioModel();

        BeanUtils.copyProperties(usuarioDto, usuarioNovo);

        Optional<TipoUsuarioModel> tipoUsuario = tipoUsuarioRepository.findByNome(usuarioDto.tipo_User());

        if (tipoUsuario.isPresent()) {
            usuarioNovo.setTipousuario(tipoUsuario.get());
        }else{
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body("ID do Tipo Usuario não encontrado");
        }
        Optional<EnderecoModel> endereco = enderecoRepository.findById(usuarioDto.endereco_id());

        if (endereco.isPresent()) {
            usuarioNovo.setEndereco(endereco.get());
        } else {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body("id_Endereço não encontrado");
        }



        String senhaCriptografada = new BCryptPasswordEncoder().encode(usuarioDto.senha());
        usuarioNovo.setSenha(senhaCriptografada);

        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioRepository.save(usuarioNovo));
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
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User no");
        }


        UsuarioModel usuario = usuarioBuscado.get();
        BeanUtils.copyProperties(usuarioDto, usuario);

        String senhaCriptografada = new BCryptPasswordEncoder().encode(usuarioDto.senha());
        usuario.setSenha(senhaCriptografada);

        Optional<TipoUsuarioModel> tipoUsuario = tipoUsuarioRepository.findByNome(usuarioDto.tipo_User());
        if (tipoUsuario.isPresent()) {
            usuario.setTipousuario(tipoUsuario.get());
        }else{
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body("ID Tipo Usuario não encontrado");
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioRepository.save(usuario));
    }

    @Operation(summary = "Método para ALTERAR um determinado usuário especificando sua ID via MediaPart", method = "PUT")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Alteração de usuário com sucesso"),
            @ApiResponse(responseCode = "404", description = "Usuario não encontrado")
    })
    @PutMapping(value = "/media/{idUsuario}",consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<Object> editarUsuarioMedia(@PathVariable(value = "idUsuario") UUID id, @ModelAttribute @Valid UsuarioDto usuarioDto) {
        Optional<UsuarioModel> usuarioBuscado = usuarioRepository.findById(id);

        if (usuarioBuscado.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }


        UsuarioModel usuario = usuarioBuscado.get();
        BeanUtils.copyProperties(usuarioDto, usuario);

        String senhaCriptografada = new BCryptPasswordEncoder().encode(usuarioDto.senha());
        usuario.setSenha(senhaCriptografada);

        Optional<TipoUsuarioModel> tipoUsuario = tipoUsuarioRepository.findByNome(usuarioDto.tipo_User());
        if (tipoUsuario.isPresent()) {
            usuario.setTipousuario(tipoUsuario.get());
        }else{
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body("ID Tipo Usuario não encontrado");
        }

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


