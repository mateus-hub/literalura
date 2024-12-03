package com.alura.literalura.entities;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DadoResultado(
        @JsonAlias("results") List<DadoLivro> livrosData
){
}
