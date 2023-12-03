package com.senai.apiecosystem.repositories;

import com.senai.apiecosystem.models.TipoStatusModel;
import com.senai.apiecosystem.models.TipoUsuarioModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface TipoStatusRepository extends JpaRepository<TipoStatusModel, UUID> {
    Optional<TipoStatusModel> findByNome(String nome);

}
