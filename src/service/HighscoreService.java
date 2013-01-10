package service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.ArrayList;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import model.HighscoreEntry;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class HighscoreService {
        
    private ArrayList<HighscoreEntry> entries;

    public HighscoreService() {
        this.entries = new ArrayList();
    }

    public void loadEntries(URL urlGet, int limit) {
        String s = "";
        URL url;
        
        try {
            url = new URL(urlGet.toString() + "?limit=" + limit);
            BufferedReader reader = null;
            
            try {
                reader = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"));

                for (String line; (line = reader.readLine()) != null;) {
                    s += line;
                }
            } finally {
                if (reader != null) try { reader.close(); } catch (IOException ignore) {}
            }
        } catch (UnsupportedEncodingException e) {
            System.out.println("ERROR: UnsupportedEncodingException thrown.");
            System.exit(1);
        } catch (IOException e) {
            System.out.println("ERROR: UnsupportedEncodingException thrown.");
            System.exit(1);
        }
        
        this.entries = this.parseXML(s);
    }

    public boolean addEntry(HighscoreEntry entry, URL urlAdd, String token) {
        String s = "";
        URL url;
        
        try {
            url = new URL(urlAdd.toString() + "?token=" + token
                    + "&player_name=" + entry.getPlayerName()
                    + "&score=" + entry.getScore()
                    + "&difficulty=" + entry.getDifficulty());
            BufferedReader reader = null;
            
            try {
                reader = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"));

                for (String line; (line = reader.readLine()) != null;) {
                    s += line;
                }
            } finally {
                if (reader != null) try { reader.close(); } catch (IOException ignore) {}
            }
        } catch (UnsupportedEncodingException e) {
            System.out.println("ERROR: UnsupportedEncodingException thrown.");
            System.exit(1);
        } catch (IOException e) {
            System.out.println("ERROR: UnsupportedEncodingException thrown.");
            System.exit(1);
        }
        return (s.equals("<status>ok</status>")) ? true : false;
    }
    
    public ArrayList<HighscoreEntry> parseXML(String s) {
            SAXParserFactory factory = SAXParserFactory.newInstance();

            try {
                    SAXParser saxParser = factory.newSAXParser();

                    HighscoreSaxHandler handler = new HighscoreSaxHandler();
                    saxParser.parse(new InputSource(new StringReader(s)), handler);
            
                    return handler.getEntries();

            } catch (ParserConfigurationException e) {
                    System.out.println("ERROR: ParserConfigurationException thrown");
                    System.exit(1);
            } catch (SAXException e) {
                    System.out.println("ERROR: SAXException thrown");
                    System.exit(1);
            } catch (IOException e) {
                    System.out.println("ERROR: IOException thrown");
                    System.exit(1);
            }
            
            return (ArrayList) null;
    }

    public ArrayList<HighscoreEntry> getEntries() {
        return entries;
    }
}
