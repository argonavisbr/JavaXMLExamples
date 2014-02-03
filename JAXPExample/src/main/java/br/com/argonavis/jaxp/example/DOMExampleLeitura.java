package br.com.argonavis.jaxp.example;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

/**
 * Hello world!
 *
 */
public class DOMExampleLeitura {

    public static final String CAMINHO = "src/fontes";

    public static void main(String[] args) throws IOException, ParserConfigurationException, SAXException {
        // Carregar XMLs de filmes
        List<File> arquivosXML = readFiles();

        List<Filme> filmes = new ArrayList<>();
        List<Diretor> diretores = new ArrayList<>();

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();

        // Ler dados de XML e montar lista de filmes e diretores
        for (File f : arquivosXML) {
            Document document = builder.parse(f);
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

            filmes.add(filme);
        }

        System.out.println("Lista de diretores");
        for (Diretor diretor : diretores) {
            StringBuilder sb = new StringBuilder();
            sb.append("\n").append(diretor.getNome());
            sb.append("\nFilmes:");
            sb.append(listarFilmes(diretor.getFilmes(), true));
            System.out.println(sb.toString());
        }

        System.out.println("\nLista de filmes");
        System.out.println(listarFilmes(filmes, false));

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
}
