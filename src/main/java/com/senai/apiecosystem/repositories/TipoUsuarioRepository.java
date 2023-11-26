package com.senai.apiecosystem.repositories;

import com.senai.apiecosystem.models.TipoUsuarioModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface TipoUsuarioRepository extends JpaRepository<TipoUsuarioModel, UUID> {
    Optional<TipoUsuarioModel> findByNome(String nome);
}
