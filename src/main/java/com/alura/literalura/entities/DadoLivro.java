package com.alura.literalura.entities;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DadoLivro(
        @JsonAlias("title")String titulo,
        @JsonAlias("authors") List<DadoAutor> autores,
        @JsonAlias("languages") List<String> linguagem,
        @JsonAlias("download_count") Integer quantidadeDownloads
) {
}
