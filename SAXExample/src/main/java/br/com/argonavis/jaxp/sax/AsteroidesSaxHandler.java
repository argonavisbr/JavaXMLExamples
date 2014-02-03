/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.argonavis.jaxp.sax;

import java.util.ArrayList;
import java.util.List;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 *
 * @author helderdarocha
 */
class AsteroidesSaxHandler extends DefaultHandler {
    
    private double maiorDiametro = 0;
    private int    maiorPeriodoOrbital = 0;
    private double maiorOrbitaMedia = 0;
    private List<Asteroide> asteroidesComSatelites = new ArrayList<>();
            
    private Asteroide currentAsteroide = null;
    private Asteroide asteroideMaiorDiametro = null;
    private Asteroide asteroideMaiorPeriodoOrbital = null;
    private Asteroide asteroideMaiorOrbitaMedia = null;

    @Override
    public void startElement(String uri, String localName, String qName, Attributes atts) throws SAXException {
        if(qName.equals("asteroide")) {
            double diametroKm = 0;
            try {
                String d = atts.getValue("diametrokm");
                if (d != null) {
                    diametroKm = Double.parseDouble(d); 
                }
            } catch (NumberFormatException e) {}
            
            int periodoOrbitalD = 0;
            try {
                String d = atts.getValue("periodoOrbitalD");
                if (d != null) {
                    periodoOrbitalD = Integer.parseInt(d);
                }
            } catch (NumberFormatException e) {}
            
            double raioMedUA = 0;
            try {
                String d = atts.getValue("raioMedUA");
                if (d != null) {
                    raioMedUA = Double.parseDouble(d);
                }
            } catch (NumberFormatException e) {}
            
            currentAsteroide = new Asteroide(atts.getValue("nome"), diametroKm, periodoOrbitalD, raioMedUA);
            
            if (diametroKm > maiorDiametro) {
                maiorDiametro = diametroKm;
                asteroideMaiorDiametro = currentAsteroide;
            }
            if (periodoOrbitalD > maiorPeriodoOrbital) {
                maiorPeriodoOrbital = periodoOrbitalD;
                asteroideMaiorPeriodoOrbital = currentAsteroide;
            }
            if (raioMedUA > maiorOrbitaMedia) {
                maiorOrbitaMedia = raioMedUA;
                asteroideMaiorOrbitaMedia = currentAsteroide;
            }
        }
        
        if(qName.equals("satelite") && currentAsteroide != null) {
            currentAsteroide.setSatelites(currentAsteroide.getSatelites() + 1);
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if(qName.equals("asteroide")) {
            if (currentAsteroide.getSatelites() > 0) {
                asteroidesComSatelites.add(currentAsteroide);
            }
            currentAsteroide = null;
        }
    }

    @Override
    public void endDocument() throws SAXException {
        System.out.println("Asteroide com maior diametro:");
        System.out.println(asteroideMaiorDiametro.getNome() + " com " + asteroideMaiorDiametro.getDiametroKm() + " km");
        System.out.println("Asteroide com maior periodo orbital:");
        System.out.println(asteroideMaiorPeriodoOrbital.getNome() + " com ano de " + asteroideMaiorPeriodoOrbital.getPeriodoOrbitalDias() + " dias");
        System.out.println("Asteroide com maior orbita:");
        System.out.println(asteroideMaiorOrbitaMedia.getNome() + " com orbita de raio " + asteroideMaiorOrbitaMedia.getRaioMedioOrbitaUA() + " UA");
        System.out.println("Asteroides com satelites:");
        for(Asteroide a : asteroidesComSatelites) {
            System.out.println("  " + a.getNome() + " com " + a.getSatelites() + ".");
        }
    }

}
