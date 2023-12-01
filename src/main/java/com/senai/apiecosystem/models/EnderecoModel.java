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
@Table(name = "tb_endereco")
public class EnderecoModel implements Serializable{
    @Serial
    private static final long serialVersionUID = 1;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private UUID id;

    private String logradouro;

    private String numero;

    private String bairro;

    private String cidade;

    private String estado;

    private String cep;



    @OneToOne
    @JoinColumn(name = "id_tipoendereco", referencedColumnName = "id")
    private EnderecoModel tipoendereco;

    @OneToOne
    @JoinColumn(name = "id_endereco", referencedColumnName = "id")
    private EnderecoModel endereco;

}