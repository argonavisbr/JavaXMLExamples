package br.com.argonavis.extractnodeswithsax;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

/**
 * Hello world!
 *
 */
public class SAXReaderExample {
    
    public static final String PATH = "src/main/resources";

    public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException {
        SAXParserFactory spf = SAXParserFactory.newInstance();
        SAXParser sp = spf.newSAXParser();
        XMLReader reader = sp.getXMLReader();
        reader.setContentHandler(new ExtractMovieSaxHandler());
        reader.parse(new InputSource(new FileInputStream(new File(PATH, "source.xml"))));
    }
}
