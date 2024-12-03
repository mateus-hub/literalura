package com.alura.literalura.entities;

import jakarta.persistence.*;

@Entity
public class Livro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titulo;
    private String linguagem;
    private Integer quantidadeDownloads;

    @ManyToOne
    private Autor autores;

    public Livro() {
    }

    public Livro(DadoLivro dadoLivro) {
        this.titulo = dadoLivro.titulo();
        Autor autor = new Autor(dadoLivro.autores().get(0));
        this.autores = autor;
        this.linguagem = dadoLivro.linguagem().get(0);
        this.quantidadeDownloads = dadoLivro.quantidadeDownloads();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getLinguagem() {
        return linguagem;
    }

    public void setLinguagem(String linguagem) {
        this.linguagem = linguagem;
    }

    public Integer getQuantidadeDownloads() {
        return quantidadeDownloads;
    }

    public void setQuantidadeDownloads(Integer quantidadeDownloads) {
        this.quantidadeDownloads = quantidadeDownloads;
    }

    public Autor getAutores() {
        return autores;
    }

    public void setAutores(Autor autores) {
        this.autores = autores;
    }

    @Override
    public String toString() {
        return "----- LIVRO -----" +
                "\nTítulo: " + titulo +
                "\nAutor: " + (autores != null ? autores.getNomeAutor() : "Desconhecido") +
                "\nIdioma: " + linguagem +
                "\nNúmero de downloads: " + quantidadeDownloads +
                "\n-----------------\n";
    }
}
