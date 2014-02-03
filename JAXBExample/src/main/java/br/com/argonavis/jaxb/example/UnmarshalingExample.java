package br.com.argonavis.jaxb.example;

import br.com.argonavis.filme.Filme;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import org.eclipse.persistence.jaxb.JAXBContextFactory;
import org.eclipse.persistence.jaxb.MarshallerProperties;
import org.eclipse.persistence.jaxb.UnmarshallerProperties;

/**
 * Hello world!
 *
 */
public class UnmarshalingExample {

    public static final String CAMINHO = "src/fontes";

    public static void main(String[] args) throws JAXBException, IOException {
        //Unmarshaller u = xmlUnmarshaller();
        //String extension = ".xml";
        Unmarshaller u = jsonUnmarshaller();
        String extension = ".json";

        List<File> arquivos = readFiles(extension);
        for (File file : arquivos) {
            Object result = u.unmarshal(file);
            Filme f;
            if (result instanceof JAXBElement) {
                f = (Filme) ((JAXBElement)result).getValue();
            } else {
                f = (Filme) result;
            }
            printFilme(f);
        }
    }

    private static Unmarshaller xmlUnmarshaller() throws JAXBException, IOException {
        JAXBContext jc = JAXBContext.newInstance("br.com.argonavis.filme");
        return jc.createUnmarshaller();
    }

    private static Unmarshaller jsonUnmarshaller() throws JAXBException, IOException {
        Map<String, Object> props = new HashMap<>();
        props.put(UnmarshallerProperties.MEDIA_TYPE, "application/json");
        props.put(UnmarshallerProperties.JSON_INCLUDE_ROOT, true);
        JAXBContext jc = JAXBContextFactory.createContext(new Class[]{Filme.class}, props);
        return jc.createUnmarshaller();
    }

    private static List<File> readFiles(String extension) throws IOException {
        File diretorio = new File(CAMINHO);
        String[] filenames = diretorio.list(new FilenameFilter() {
            public boolean accept(File dir, String name) {
                if (name.endsWith(extension)) {
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

    private static void printFilme(Filme f) {
        StringBuilder builder = new StringBuilder();
        builder.append("\nID: ");
        builder.append(f.getId());
        builder.append("\nIMDB: ");
        builder.append(f.getImdb());
        builder.append("\nTitulo: ");
        builder.append(f.getTitulo());
        builder.append("\nDiretor: ");
        builder.append(f.getDiretor());
        builder.append("\nAno: ");
        builder.append(f.getAno());
        builder.append("\nDuracao: ");
        builder.append(f.getDuracao());
        builder.append("\n");
        System.out.println(builder.toString());
    }

}
