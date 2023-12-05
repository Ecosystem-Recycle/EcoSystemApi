package com.senai.apiecosystem.repositories;

import com.senai.apiecosystem.models.CategoriaModel;
import com.senai.apiecosystem.models.TipoStatusModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CategoriaRepository extends JpaRepository<CategoriaModel, UUID> {
    Optional<CategoriaModel> findByNome(String nome);
}
