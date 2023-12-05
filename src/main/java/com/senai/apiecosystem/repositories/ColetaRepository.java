package com.senai.apiecosystem.repositories;

import com.senai.apiecosystem.models.ColetaModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ColetaRepository extends JpaRepository<ColetaModel, UUID> {
}
