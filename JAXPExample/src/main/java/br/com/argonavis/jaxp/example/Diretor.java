/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.argonavis.jaxp.example;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author helderdarocha
 */
public class Diretor {
    private String nome;
    private List<Filme> filmes;

    public Diretor(String nome) {
        this.nome = nome;
        this.filmes = new ArrayList<>();
    }
    
    public void addFilme(Filme filme) {
        filmes.add(filme);
        filme.setDiretor(this);
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public List<Filme> getFilmes() {
        return filmes;
    }

    public void setFilmes(List<Filme> filmes) {
        this.filmes = filmes;
    }
}
