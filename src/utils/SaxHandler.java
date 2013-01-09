package utils;

import java.util.ArrayList;
import model.elements.Player;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class SaxHandler extends DefaultHandler {
    
    private ArrayList<String> file;
    private ArrayList<Object> level = new ArrayList();
    private ArrayList<ArrayList> levels = new ArrayList();
    
    public ArrayList parseFile() {
        
        for (int i=0; i < file.size(); i++) {
            if (file.get(i).equals("<players>")){
                Boolean players = true;
                int c = 0;
                while(players) {
                    c++; // Counter
                    
                    level.add(new Player());
                    
                    // Check if end of tag
                    players = (file.get(i+c).equals("</players>")) ? false : true;
                }
            }
        }
        
        levels.add(level);
        
        return levels;
    }
            
    
    public ArrayList getFile() {
        return file;
    }
    
    private void printFile() {
        if (file != null) {
            int c = 0;
            for (String s : file) {
                // Indent
                c = s.contains("/") ? c-1 : c+1;
                for (int i = 0; i < c; i++) {
                    System.out.print("\t");
                }

                System.out.println(s);
            }
        } else {
            System.out.println("ERROR: null file");
            System.exit(1);
        }
    }
    
    @Override
    public void startDocument() throws SAXException {
        if (file == null) {
            file = new ArrayList();
        } else {
            System.out.println("ERROR: File already parsed");
            System.exit(1);
        }
    }

    @Override
    public void endDocument() throws SAXException {
        
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes)
    throws SAXException {
        
        // Format line
        String line = "<" + qName;
        for (int i=0; i < attributes.getLength(); i++) {
            line = line + " " + attributes.getValue(i);
        }
        line = line + ">";
        
        file.add(line); // Add to array
    }

    @Override
    public void endElement(String uri, String localName, String qName)
    throws SAXException {
        
        String line = "</" + qName + ">"; // Format line
        
        file.add(line); // Add to file
    }

    @Override
    public void characters(char ch[], int start, int length)
    throws SAXException {
        //System.out.println("start characters : " + new String(ch, start, length));
    }
    
}
