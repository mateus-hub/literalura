package com.alura.literalura.entities;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DadoAutor(
        @JsonAlias("name") String nomeAutor,
        @JsonAlias("birth_year") Integer anoDeNascimento,
        @JsonAlias("death_year") Integer anoDaMorte
) {
}