/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package exercicios;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

/**
 *
 * @author helderdarocha
 */
public class Exercicio1 {
    
    public static final String FONTES     = "src/fontes";
    public static final String RESULTADOS = "target/exercicio2";
    
    public static void main(String[] args) throws SAXException, IOException, ParserConfigurationException, TransformerException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = factory.newDocumentBuilder();
        
        Document newDocument = docBuilder.newDocument();
        Element filmesTag = newDocument.createElement("filmes");
        
        // 1 - Leitura do XML
        List<File> xmls = readFiles();
        for(File file : xmls) {
            Document document = docBuilder.parse(file);
            Element tituloElement = (Element)document.getElementsByTagName("titulo").item(0);
            String titulo = tituloElement.getTextContent();
            
            String codigo = document.getDocumentElement().getAttribute("imdb");
            System.out.println(codigo + ": " + titulo);
            
            //<titulo imdb=“yyy1”>xxx1</titulo> 
            Element tituloTag = newDocument.createElement("titulo");
            Node tituloValue  = newDocument.createTextNode(titulo);
            tituloTag.appendChild(tituloValue);
            tituloTag.setAttribute("codigo", codigo);
            filmesTag.appendChild(tituloTag);
        }
        
        // 2 - Geracao do XML
        serializeDocument("titulos", filmesTag);
        
        
    }
    
    private static void serializeDocument(String nome, Element rootElement) throws TransformerConfigurationException, TransformerException, FileNotFoundException {
        String nomeArquivo = new StringBuilder(nome.replace(" ", "_").replace(".", "")).append(".xml").toString();
        
        TransformerFactory tFactory = TransformerFactory.newInstance();
        Transformer transformer = tFactory.newTransformer();
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        
        DOMSource source = new DOMSource(rootElement);
        File diretorio = new File(RESULTADOS);
        diretorio.mkdir();
        File file = new File(diretorio, nomeArquivo);
        StreamResult result = new StreamResult(new FileOutputStream(file));
        System.out.println("Gravado " + file);
        transformer.transform(source, result);
    }
    
    private static List<File> readFiles() throws IOException {
        File diretorio = new File(FONTES);
        String[] filenames = diretorio.list(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                if (name.endsWith(".xml")) {
                    return true;
                }
                return false;
            }
        });

        List<File> xmlFiles = new ArrayList<>();
        for (String filename : filenames) {
            File f = new File(diretorio, filename);
            xmlFiles.add(f);
        }
        return xmlFiles;
    }
}
