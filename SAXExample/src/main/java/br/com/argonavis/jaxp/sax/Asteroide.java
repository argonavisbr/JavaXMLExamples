/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.argonavis.jaxp.sax;

/**
 *
 * @author helderdarocha
 */
public class Asteroide {
    private String nome;
    private double diametroKm;
    private double periodoOrbitalDias;
    private double raioMedioOrbitaUA;
    private int satelites;

    public Asteroide(String nome, double diametroKm, double periodoOrbitalDias, double raioMedioOrbitaUA) {
        this.nome = nome;
        this.diametroKm = diametroKm;
        this.periodoOrbitalDias = periodoOrbitalDias;
        this.raioMedioOrbitaUA = raioMedioOrbitaUA;
    }

    public int getSatelites() {
        return satelites;
    }

    public void setSatelites(int satelites) {
        this.satelites = satelites;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public double getDiametroKm() {
        return diametroKm;
    }

    public void setDiametroKm(double diametroKm) {
        this.diametroKm = diametroKm;
    }

    public double getPeriodoOrbitalDias() {
        return periodoOrbitalDias;
    }

    public void setPeriodoOrbitalDias(double periodoOrbitalDias) {
        this.periodoOrbitalDias = periodoOrbitalDias;
    }

    public double getRaioMedioOrbitaUA() {
        return raioMedioOrbitaUA;
    }

    public void setRaioMedioOrbitaUA(double raioMedioOrbitaUA) {
        this.raioMedioOrbitaUA = raioMedioOrbitaUA;
    }
   
    
            
}
