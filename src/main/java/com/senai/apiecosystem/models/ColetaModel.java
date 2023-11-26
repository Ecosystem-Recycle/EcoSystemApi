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
@Table(name = "coleta")
public class ColetaModel implements Serializable{
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private UUID id;

    private LocalDate data_cadastro;

    private String disponibilidade;

    @ManyToOne
    @JoinColumn(name = "usuario_id", referencedColumnName = "id")
    private UsuarioModel usuario;

    @OneToOne
    @JoinColumn(name = "anuncio_id", referencedColumnName = "id")
    private AnuncioModel anuncio;

    @ManyToOne
    @JoinColumn(name = "tipo_status_id", referencedColumnName = "id")
    private TipoStatusModel tipo_status;


}