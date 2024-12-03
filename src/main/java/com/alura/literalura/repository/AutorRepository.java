package com.alura.literalura.repository;

import com.alura.literalura.entities.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AutorRepository extends JpaRepository<Autor, Long> {

    @Query("SELECT a FROM Livro l JOIN l.autores a WHERE a.nomeAutor = :nomeAutor")
    Autor buscarAutorPeloNome(String nomeAutor);

    @Query("SELECT a FROM Livro l JOIN l.autores a WHERE a.anoDeNascimento <= :anoDigitado and a.anoDaMorte >= :anoDigitado")
    List<Autor> listaDeAutoresVivosEmDeterminadoAno(Integer anoDigitado);
}

