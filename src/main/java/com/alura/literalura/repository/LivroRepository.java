package com.alura.literalura.repository;

import com.alura.literalura.entities.Livro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LivroRepository extends JpaRepository<Livro, Long> {

    @Query("SELECT l FROM Livro l WHERE l.linguagem ILIKE :idioma")
    List<Livro> buscarLivroPeloIdioma(String idioma);
}
