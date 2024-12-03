package com.alura.literalura.main;

import com.alura.literalura.entities.*;
import com.alura.literalura.repository.AutorRepository;
import com.alura.literalura.repository.LivroRepository;

import com.alura.literalura.service.ConsumoAPI;
import com.alura.literalura.service.ConverteDados;

import java.util.*;

public class Main {

    private Scanner scanner = new Scanner(System.in);

    private ConsumoAPI consumo = new ConsumoAPI();
    private final String BASE_URL = "https://gutendex.com/books/?search=";
    private ConverteDados conversor = new ConverteDados();

    private LivroRepository livroRepository;
    private AutorRepository autorRepository;

    private List<Autor> autores = new ArrayList<>();

    public Main(LivroRepository livroRepository, AutorRepository autorRepository) {
        this.livroRepository = livroRepository;
        this.autorRepository = autorRepository;
    }

    public void mostrarMenu() {
        var opcao = -1;
        while (opcao != 0) {
            System.out.println("""
                    -----------
                    Escolha o número de sua opção:                    
                    1 - buscar livro por título
                    2 - listar livros registrados
                    3 - listar autores registrados
                    4 - listar autores vivos em um determinado ano 
                    5 - listar livros em um determinado idioma                 
                    0 - Sair
                    """);
            try {
                opcao = scanner.nextInt();
                scanner.nextLine();
            } catch (InputMismatchException e) {
                opcao = -1;
                scanner.nextLine();
            }

            switch (opcao) {
                case 1:
                    buscarLivroPorTitulo();
                    break;
                case 2:
                    listarLivrosRegistrados();
                    break;
                case 3:
                    listarAutoresRegistrados();
                    break;
                case 4:
                    listarAutoresVivosEmData();
                    break;
                case 5:
                    buscarLivrosPorIdioma();
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Opção inválida");
            }
        }
    }

    private void buscarLivroPorTitulo() {
        DadoResultado dadoResultado = getDadosLivro();
        List<DadoLivro> dadoLivro = dadoResultado.livrosData();

        if (!dadoLivro.isEmpty()) {
            DadoLivro livroExistente = dadoLivro.get(0);
            Livro livro = new Livro(livroExistente);

            Autor autorExistente = autorRepository.buscarAutorPeloNome(livro.getAutores().getNomeAutor());

            if (autorExistente == null) {
                autorRepository.save(livro.getAutores());
            } else {
                livro.setAutores(autorExistente);
            }

            livroRepository.save(livro);
            System.out.println(livro);
        } else {
            System.out.println("Livro não encontrado");
        }

    }

    public DadoResultado getDadosLivro() {
        System.out.println("\nInsira o nome do livro que você deseja procurar ");
        String nomeLivro = scanner.nextLine();
        var json = consumo.obterDados(BASE_URL + nomeLivro.replace(" ", "+"));
        System.out.println(json);
        DadoResultado dado = conversor.obterDados(json, DadoResultado.class);
        return dado;
    }

    private void listarLivrosRegistrados() {
        List<Livro> livro = livroRepository.findAll();
        if (livro.isEmpty()) {
            System.out.println("Ainda não há registros na base de dados");
            return;
        }
        livro.stream()
                .sorted(Comparator.comparing(Livro::getTitulo))
                .forEach(System.out::println);
    }

    private void listarAutoresRegistrados() {
        autores = autorRepository.findAll();
        if (autores.isEmpty()) {
            System.out.println("Ainda não há autores cadastrados na base de dados");
            return;
        }
        autores.stream()
                .sorted(Comparator.comparing(Autor::getNomeAutor))
                .forEach(System.out::println);
    }

    private void listarAutoresVivosEmData() {
        System.out.println("\nInsira o ano que deseja pesquisar");
        var busca = scanner.nextLine();
        try {
            Integer data = Integer.parseInt(busca);
            List<Autor> autoresVivosEmData = autorRepository.listaDeAutoresVivosEmDeterminadoAno(data);
            if (autoresVivosEmData.isEmpty()) {
                System.out.println("Não há autores vivos nessa data");
                return;
            }
            autoresVivosEmData.stream().sorted(Comparator.comparing(Autor::getNomeAutor))
                    .forEach(System.out::println);
        } catch (NumberFormatException e) {
            System.out.println("\n" +
                    "Os dados inseridos não são um número inteiro. tente novamente");
        }
    }

    private void buscarLivrosPorIdioma() {
        System.out.println("""
                Escolha um idioma para filtrar:
                es- espanhol
                en- inglês
                fr- francês
                pt- português
                """);
        var idioma = scanner.nextLine();

        List<Livro> livrosIdioma = livroRepository.buscarLivroPeloIdioma(idioma);
        livrosIdioma.forEach(System.out::println);

        if (livrosIdioma.isEmpty()) {
            System.out.println("Não existem livros nesse idioma no banco de dados.");
        }
    }
}
