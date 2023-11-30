package com.senai.apiecosystem.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "anuncio")
public class AnuncioModel implements Serializable{
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private UUID id;

    private String titulo;

    private LocalDate data_cadastro;

    private String disponibilidade;

    private String periodo;

    private String url_imagem;

    @OneToOne
    @JoinColumn(name = "usuario_id", referencedColumnName = "id")
    private UsuarioModel usuario_doador;

    @OneToOne
    @JoinColumn(name = "tipo_status_id", referencedColumnName = "id")
    private TipoStatusModel tipo_status_anuncio;

}