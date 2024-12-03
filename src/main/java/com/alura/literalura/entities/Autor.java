package com.alura.literalura.entities;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
public class Autor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nomeAutor;
    private Integer anoDeNascimento;
    private Integer anoDaMorte;

    @OneToMany(mappedBy = "autores", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Livro> livros = new ArrayList<>();

    public Autor(){}

    public Autor(DadoAutor dadoAutor)
    {
        this.nomeAutor = dadoAutor.nomeAutor();
        this.anoDeNascimento = dadoAutor.anoDeNascimento();
        try {
            this.anoDaMorte = dadoAutor.anoDaMorte();
        } catch (Exception e) {
            this.anoDaMorte = null;
        }
    }

    public Autor (String nomeAutor, Integer anoDeNascimento, Integer anoDaMorte) {
        this.nomeAutor = nomeAutor;
        this.anoDeNascimento = anoDeNascimento;
        this.anoDaMorte = anoDaMorte;
    }

    public Long getId() {
        return id;
    }

    public String getNomeAutor() {
        return nomeAutor;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getAnoDeNascimento() {
        return anoDeNascimento;
    }

    public void setAnoDeNascimento(Integer anoDeNascimento) {
        this.anoDeNascimento = anoDeNascimento;
    }

    public Integer getAnoDaMorte() {
        return anoDaMorte;
    }

    public void setAnoDaMorte(Integer anoDaMorte) {
        this.anoDaMorte = anoDaMorte;
    }

    public List<String> getLivros() {
        return livros.stream()
                .map(Livro::getTitulo).collect(Collectors.toList());
    }

    public void setLivros(List<Livro> livros) {
        livros.forEach(l -> l.setAutores(this));
        this.livros = livros;
    }

    @Override
    public String toString() {
        String livrosStr = livros.stream()
                .map(Livro::getTitulo)
                .collect(Collectors.joining(", "));
        return "\nAutor: " + nomeAutor +
                "\nAno de nascimento: " + anoDeNascimento +
                "\nAno de falecimento: " + anoDaMorte +
                "\nLivros: [" + livrosStr + ']';
    }
}

