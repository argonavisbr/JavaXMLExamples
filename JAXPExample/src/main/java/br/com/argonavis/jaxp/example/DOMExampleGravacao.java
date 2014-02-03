package br.com.argonavis.jaxp.example;

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
import org.xml.sax.SAXException;

/**
 * Hello world!
 *
 */
public class DOMExampleGravacao {

    public static final String CAMINHO           = "src/fontes";
    public static final String CAMINHO_RESULTADO = "target/resultado";

    public static void main(String[] args) throws IOException, ParserConfigurationException, SAXException, TransformerException {
        // Carregar XMLs de filmes
        List<File> arquivosXML = readFiles();
        List<Diretor> diretores = new ArrayList<>();

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = factory.newDocumentBuilder();

        // Ler dados de XML e montar lista de diretores
        for (File f : arquivosXML) {
            Document document = documentBuilder.parse(f);
            Element root = document.getDocumentElement();
            root.removeAttribute("id");
            Filme filme = new Filme();
            filme.setImdb(root.getAttribute("imdb"));
            filme.setTitulo(root.getElementsByTagName("titulo").item(0).getTextContent());
            filme.setAno(Integer.parseInt(root.getElementsByTagName("ano").item(0).getTextContent()));
            filme.setDuracao(Integer.parseInt(root.getElementsByTagName("duracao").item(0).getTextContent()));

            String nomeDiretor = root.getElementsByTagName("diretor").item(0).getTextContent();
            Diretor diretor = findDiretor(nomeDiretor, diretores);
            diretor.addFilme(filme);
        }

        // Gravar diretores em arquivos XML
        for (Diretor diretor : diretores) {
            Document newDocument = documentBuilder.newDocument();
            Element rootElement = newDocument.createElement("diretor");

            Element nomeElement = newDocument.createElement("nome");
            nomeElement.appendChild(newDocument.createTextNode(diretor.getNome()));

            Element filmesElement = newDocument.createElement("filmes");
            for (Filme filme : diretor.getFilmes()) {
                Element filmeElement = newDocument.createElement("filme");
                filmeElement.appendChild(newDocument.createTextNode(filme.getTitulo()));
                filmeElement.setAttribute("imdb", filme.getImdb());
                filmeElement.setAttribute("duracao", ""+filme.getDuracao());

                filmesElement.appendChild(filmeElement);
            }

            rootElement.appendChild(nomeElement);
            rootElement.appendChild(filmesElement);

            // do something with Document now!
            serializeDocument(diretor.getNome(), rootElement);
        }

    }

    private static List<File> readFiles() throws IOException {
        File diretorio = new File(CAMINHO);
        String[] filenames = diretorio.list(new FilenameFilter() {
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

    private static Diretor findDiretor(String nomeDiretor, List<Diretor> diretores) {
        for (Diretor diretor : diretores) {
            if (nomeDiretor.equals(diretor.getNome())) {
                return diretor;
            }
        }
        Diretor novo = new Diretor(nomeDiretor);
        diretores.add(novo);
        return novo;
    }

    private static String listarFilmes(List<Filme> filmes, boolean shortVersion) {
        StringBuilder builder = new StringBuilder();
        for (Filme filme : filmes) {
            if (shortVersion) {
                builder.append("\n  ").append(filme.getTitulo());
                builder.append(" (").append(filme.getAno()).append(")");
            } else {
                builder.append("\n").append(filme.getImdb()).append(": ");
                builder.append(filme.getTitulo());
                builder.append(" (").append(filme.getAno()).append(") ");
                builder.append(filme.getDiretor().getNome());
                builder.append(", ").append(filme.getDuracao()).append(" minutos\n");
            }
        }
        return builder.toString();
    }

    private static void serializeDocument(String nome, Element rootElement) throws TransformerConfigurationException, TransformerException, FileNotFoundException {
        String nomeArquivo = new StringBuilder(nome.replace(" ", "_").replace(".", "")).append(".xml").toString();
        
        TransformerFactory tFactory = TransformerFactory.newInstance();
        Transformer transformer = tFactory.newTransformer();
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        
        DOMSource source = new DOMSource(rootElement);
        File diretorio = new File(CAMINHO_RESULTADO);
        diretorio.mkdir();
        File file = new File(diretorio, nomeArquivo);
        StreamResult result = new StreamResult(new FileOutputStream(file));
        System.out.println("Gravado " + file);
        transformer.transform(source, result);
    }
}
