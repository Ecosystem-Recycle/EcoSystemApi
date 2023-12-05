package com.senai.apiecosystem.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "tb_coleta")

public class ColetaModel implements Serializable{
    @Serial
    private static final long serialVersionUID = 1;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private UUID id;

    private String disponibilidade;
    private Date data_cadastro;

    @OneToOne
    @JoinColumn(name = "anuncio_id", referencedColumnName = "id")
    private ColetaModel anuncio;
    @OneToOne
    @JoinColumn(name = "tipo_status_id", referencedColumnName = "id")
    private ColetaModel tipo_status;
    @OneToOne
    @JoinColumn(name = "usuario_id", referencedColumnName = "id")
    private ColetaModel usuario;
}
