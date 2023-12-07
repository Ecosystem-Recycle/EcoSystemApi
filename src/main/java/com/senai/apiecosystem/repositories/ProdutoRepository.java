package com.senai.apiecosystem.repositories;


import com.senai.apiecosystem.models.ProdutoModel;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
<<<<<<< HEAD
import org.springframework.data.jpa.repository.QueryRewriter;
import org.springframework.data.repository.query.Param;
=======

>>>>>>> 4a1eb62fa540bfb2800c0873500bc69ea4962cba
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ProdutoRepository extends JpaRepository<ProdutoModel, UUID> {
<<<<<<< HEAD
    @Query(value = "SELECT id, nome, quantidade, anuncio_id,categoria_id FROM produto WHERE anuncio_id = ?1", nativeQuery = true)
    List<ProdutoModel> findProdAnuncioById(UUID id);
}


=======


}



>>>>>>> 4a1eb62fa540bfb2800c0873500bc69ea4962cba
