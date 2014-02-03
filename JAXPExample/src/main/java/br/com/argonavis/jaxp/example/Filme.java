/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.argonavis.jaxp.example;

/**
 *
 * @author helderdarocha
 */
public class Filme {
    private String imdb;
    private String titulo;
    private Diretor diretor;
    private int ano;
    private int duracao;

    public Filme() {
    }

    public Filme(String imdb, String titulo, Diretor diretor, int ano, int duracao) {
        this.imdb = imdb;
        this.titulo = titulo;
        this.diretor = diretor;
        this.ano = ano;
        this.duracao = duracao;
    }

    public String getImdb() {
        return imdb;
    }

    public void setImdb(String imdb) {
        this.imdb = imdb;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Diretor getDiretor() {
        return diretor;
    }

    public void setDiretor(Diretor diretor) {
        this.diretor = diretor;
    }

    public int getAno() {
        return ano;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }

    public int getDuracao() {
        return duracao;
    }

    public void setDuracao(int duracao) {
        this.duracao = duracao;
    }
}
