package com.senai.apiecosystem.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "produto")
public class ProdutoModel implements Serializable{
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private UUID id;

    private String nome;

    private Integer quantidade;

    @OneToOne
    @JoinColumn(name = "categoria_id", referencedColumnName = "id")
    private CategoriaModel categoria;

    @ManyToOne
    @JoinColumn(name = "anuncio_id", referencedColumnName = "id")
    private AnuncioModel anuncio;
}