package br.com.argonavis.jaxb.example;

import br.com.argonavis.filme.Filme;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

// EclipseLink MOXy (para suporte JSON)
import org.eclipse.persistence.jaxb.JAXBContextFactory;
import org.eclipse.persistence.jaxb.MarshallerProperties;
import org.eclipse.persistence.jaxb.UnmarshallerProperties;

public class MarshalingExample {
    
    public static final String CAMINHO = "target/resultado";

    public static void main(String[] args) throws JAXBException {

        createFilme("The Shining", "Stanley Kubrick", "tt0081505", 1980, 144);
        createFilme("Melancolia", "Lars von Trier", "tt1527186", 2011, 136);
        createFilme("Annie Hall", "Woody Allen", "tt0075686", 1977, 93);
        createFilme("Fahrenheit 451", "François Truffaut", "tt0060390", 1966, 112);
        createFilme("Nosferatu, eine Symphonie des Grauens", "F. W. Murnau", "tt0013442", 1922, 81);
        createFilme("Amarcord", "Frederico Fellini", "tt0071129", 1973, 123);
        createFilme("A Clockwork Orange", "Stanley Kubrick", "tt0066921", 1972, 136);
        createFilme("La double vie de Véronique", "Krzysztof Kieslowski", "tt0101765", 1991, 98);
        createFilme("Solyaris", "Andrei Tarkovsky", "tt0069293", 1972, 167);
        createFilme("Jodaeiye Nader az Simin", "Asghar Farhadi", "tt1832382", 2011, 123);
        createFilme("Hearat Shulayim", "Joseph Cedar", "tt1445520", 2011, 103);
        createFilme("O Som ao Redor", "Kleber Mendonça Filho", "tt2190367", 2012, 131);
        createFilme("Un Cuento Chino", "Sebastián Borensztein", "tt1705786", 2011, 93);
        createFilme("El Laberinto del Fauno", "Guillermo del Toro", "tt0457430", 2006, 118);
        createFilme("Holy Motors", "Leos Carax", "tt2076220", 2012, 115);
        createFilme("La Flor de mi Secreto", "Pedro Almodovar", "tt0113083", 1995, 103);
        createFilme("La Piel que Habito", "Pedro Almodovar", "tt1189073", 2011, 120);
        createFilme("Stalker", "Andrei Tarkovsky", "tt0079944", 1979, 163);
        createFilme("Nymphomaniac", "Lars von Trier", "tt1937390", 2013, 330);
        createFilme("2001: A Space Odyssey", "Stanley Kubrick", "tt0062622", 1968, 160);
    }
    
    public static void createFilme(String titulo, String diretor, String imdb, long ano, long duracao) throws JAXBException {
        Filme filme = new Filme();
        filme.setAno(ano);
        filme.setDiretor(diretor);
        filme.setId(0);
        filme.setImdb(imdb);
        filme.setDuracao(duracao);
        filme.setTitulo(titulo);
        
        marshal(filme, "json");
        marshal(filme, "xml");
    }
    
    public static void marshal(Filme filme, String mediaType) throws JAXBException {
        
        
        JAXBContext jc;
        if (mediaType.equals("json")) { // JSON
            Map<String, Object> props = new HashMap<>();
            props.put(MarshallerProperties.MEDIA_TYPE, "application/json");
            props.put(MarshallerProperties.JSON_INCLUDE_ROOT, true);
            jc = JAXBContextFactory.createContext(new Class[]{Filme.class}, props);
        } else if (mediaType.equals("xml")) {
            jc = JAXBContext.newInstance("br.com.argonavis.filme");
        } else {
            return;
        }
        
       
        
        Marshaller m = jc.createMarshaller();
        m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        m.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
        
        File pasta = new File(CAMINHO);
        pasta.mkdir();
        File arquivo = new File(pasta, filme.getImdb() + "." + mediaType);
        m.marshal(filme, arquivo);
        System.out.println("Created " + arquivo);
    }
}
