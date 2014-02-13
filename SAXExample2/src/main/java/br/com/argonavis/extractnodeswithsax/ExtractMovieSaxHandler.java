/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.argonavis.extractnodeswithsax;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 *
 * @author helderdarocha
 */
class ExtractMovieSaxHandler extends DefaultHandler {

    // These are the parameters for the search
    private static final String TAG_TO_USE = "Director";
    private static final String[] MATCHES = {"Carax", "Trier"}; // OR match
    private static final boolean STRICT = false;  // if STRICT matches will be exact

    // These are the temporary values we store as we parse the file
    private String currentElement;
    private StringBuilder contents = null; // if not null we are in Movie tag
    private String currentData;
    List<String> result = new ArrayList<String>(); // store resulting nodes here
    private boolean skip = false;

    @Override
    public void startElement(String uri, String localName, String qName, Attributes atts) throws SAXException {

        // Store the current element that started now
        currentElement = qName;

        // If this is a Movie tag, save the contents because we might need it
        if (qName.equals("Movie")) {
            contents = new StringBuilder();
        }

    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {

        // if we discovered that we don't need this data, we skip it
        if (skip || currentElement == null) {
            return;
        }

        // If we are inside the tag we want to search, save the contents
        currentData = new String(ch, start, length);

        if (currentElement.equals(TAG_TO_USE)) {
            boolean discard = true;

            for (String match : MATCHES) {
                if (STRICT) {
                    if (currentData.equals(match)) { // exact match
                        discard = false;
                    }

                } else {
                    if (currentData.toLowerCase().indexOf(match.toLowerCase()) >= 0) { // matches occurrence of string
                        discard = false;
                    }
                }
            }

            if (discard) {
                skip = true;
            }
        }

    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {

        // Rebuild the XML if it's a node we didn't skip
        if (qName.equals("Movie")) {
            if (!skip) {
                result.add(contents.insert(0,"<Movie>").append("</Movie>").toString());
            }

            // reset the variables so we can check the next node
            contents = null;
            skip = false;
        } else if (contents != null && !skip) {
            contents.append("<").append(qName).append(">")
                    .append(currentData)
                    .append("</").append(qName).append(">");
        }

        currentElement = null;
    }

    @Override
    public void endDocument() throws SAXException {
        StringBuilder resultFile = new StringBuilder();
        resultFile.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        resultFile.append("<Movies>");
        for (String childNode : result) {
            resultFile.append(childNode.toString());
        }
        resultFile.append("</Movies>");

        System.out.println("=== Resulting XML containing Movies where " + TAG_TO_USE + " is one of " + Arrays.toString(MATCHES) + " ===");
        System.out.println(resultFile.toString());
    }

}
