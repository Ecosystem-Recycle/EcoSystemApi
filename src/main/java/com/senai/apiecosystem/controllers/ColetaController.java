package com.senai.apiecosystem.controllers;

import com.senai.apiecosystem.dtos.AnuncioDto;
import com.senai.apiecosystem.dtos.ColetaDto;
import com.senai.apiecosystem.models.AnuncioModel;
import com.senai.apiecosystem.models.ColetaModel;
import com.senai.apiecosystem.models.TipoStatusModel;
import com.senai.apiecosystem.repositories.AnuncioRepository;
import com.senai.apiecosystem.repositories.ColetaRepository;
import com.senai.apiecosystem.repositories.TipoStatusRepository;
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
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping(value = "/coleta", produces = {"application/json"})
public class ColetaController {
    @Autowired
    ColetaRepository coletaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private AnuncioRepository anuncioRepository;

    @Autowired
    private TipoStatusRepository tipoStatusRepository;

    @Operation(summary = "Método para CONSULTAR todos as coletas no sistema", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Dados retornados com sucesso")
    })
    @GetMapping
    public ResponseEntity<List<ColetaModel>> listarColetas(){
        return ResponseEntity.status(HttpStatus.OK).body(coletaRepository.findAll());
    }

    @Operation(summary = "Método para CONSULTAR uma determinada coleta especificando seu ID", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Dados retornados com sucesso"),
            @ApiResponse(responseCode = "404", description = "Coleta não encontrada")
    })
    @GetMapping("/{idColeta}")
    public ResponseEntity<Object> exibirColeta(@PathVariable(value = "idColeta") UUID id) {
        Optional<ColetaModel> coletaBuscada = coletaRepository.findById(id);

        if (coletaBuscada.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Coleta não encontrada");
        }

        return ResponseEntity.status(HttpStatus.OK).body(coletaBuscada.get());
    }

    @Operation(summary = "Método para CADASTRAR uma nova Coleta", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Coleta cadastrada com Sucesso"),
            @ApiResponse(responseCode = "400", description = "Parâmetros inválidos")
    })
    @PostMapping
    public ResponseEntity<Object> cadastrarColeta(@RequestBody @Valid ColetaDto coletaDto) {
        ColetaModel coletaModel = new ColetaModel();

        BeanUtils.copyProperties(coletaDto, coletaModel);

        var usuario = usuarioRepository.findById(coletaDto.usuario_id());
        var anuncio = anuncioRepository.findById(coletaDto.anuncio_id());
        Optional<TipoStatusModel> tipoStatus = tipoStatusRepository.findByNome(coletaDto.tipo_status());
        LocalDate date = LocalDate.now();

        if (usuario.isPresent() && anuncio.isPresent() && tipoStatus.isPresent()) {
            coletaModel.setUsuario_coleta(usuario.get());
            coletaModel.setAnuncio(anuncio.get());
            coletaModel.setTipo_status_coleta(tipoStatus.get());
            coletaModel.setData_cadastro(date);

        } else {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body("ID Usuario ou ID Anuncio ou ID Tipo de Status não encontrado");
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(coletaRepository.save(coletaModel));
    }

    @Operation(summary = "Método para CADASTRAR uma nova Coleta como Media", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Coleta cadastrada com Sucesso"),
            @ApiResponse(responseCode = "400", description = "Parâmetros inválidos")
    })
    @PostMapping(value = "/media",consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<Object> cadastrarColetaMedia(@ModelAttribute @Valid ColetaDto coletaDto) {
        ColetaModel coletaModel = new ColetaModel();

        BeanUtils.copyProperties(coletaDto, coletaModel);

        var usuario = usuarioRepository.findById(coletaDto.usuario_id());
        var anuncio = anuncioRepository.findById(coletaDto.anuncio_id());
        Optional<TipoStatusModel> tipoStatus = tipoStatusRepository.findByNome(coletaDto.tipo_status());
        LocalDate date = LocalDate.now();

        if (usuario.isPresent() && anuncio.isPresent() && tipoStatus.isPresent()) {
            coletaModel.setUsuario_coleta(usuario.get());
            coletaModel.setAnuncio(anuncio.get());
            coletaModel.setTipo_status_coleta(tipoStatus.get());
            coletaModel.setData_cadastro(date);

        } else {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body("ID Usuario ou ID Anuncio ou ID Tipo de Status não encontrado");
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(coletaRepository.save(coletaModel));
    }



    @Operation(summary = "Método para ALTERAR uma determinada coleta especificando seu ID", method = "PUT")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Dados de Coleta Alterado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Coleta não encontrada")
    })
    @PutMapping(value = "/{idColeta}")
    public ResponseEntity<Object> editarColeta(@PathVariable(value = "idColeta") UUID id, @RequestBody @Valid ColetaDto coletaDto) {
        Optional<ColetaModel> coletaBuscada = coletaRepository.findById(id);

        if (coletaBuscada.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Coleta não encontrada");
        }

        ColetaModel coleta = coletaBuscada.get();
        BeanUtils.copyProperties(coletaDto, coleta);

        return ResponseEntity.status(HttpStatus.CREATED).body(coletaRepository.save(coleta));
    }

    @Operation(summary = "Método para Alterar dados parcial de uma determinada coleta especificando seu ID", method = "PATCH")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Alteração de coleta com sucesso"),
            @ApiResponse(responseCode = "404", description = "Coleta não encontrada")
    })
    @PatchMapping(value = "/status/{idColeta}")
    public ResponseEntity<Object> editarStatusColeta(@PathVariable(value = "idColeta") UUID id,  @RequestBody ColetaDto coletaDto) {
        Optional<ColetaModel> coletaBuscada = coletaRepository.findById(id);

        if (coletaBuscada.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Coleta não encontrada");
        }

        Optional<TipoStatusModel> tipoStatus = tipoStatusRepository.findByNome(coletaDto.tipo_status());

        ColetaModel coleta = coletaBuscada.get();

        coleta.setTipo_status_coleta(tipoStatus.get());

        BeanUtils.copyProperties(coleta, coletaDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(coletaRepository.save(coleta));
    }

    @Operation(summary = "Método para DELETAR uma determinada Coleta especificando seu ID", method = "DELETE")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Coleta deletada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Coleta não encontrada")
    })
    @DeleteMapping(value = "/{idColeta}")
    public ResponseEntity<Object> deletarColeta(@PathVariable(value = "idColeta") UUID id) {
        Optional<ColetaModel> coletaBuscado = coletaRepository.findById(id);

        if (coletaBuscado.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Coleta não encontrada");
        }

        coletaRepository.delete(coletaBuscado.get());

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Coleta deletada com sucesso!");
    }

}