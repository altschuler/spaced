package service.parsing;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import model.HighscoreEntry;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

/**
 * Translates the XML-file from {@link service.HighscoreService} to an ArrayList with highscore entries ({@link model.HighscoreEntry}). 
 */
public class HighscoreSaxHandler extends AbstractSaxHandler {
    
    private ArrayList<HighscoreEntry> entries = new ArrayList<>();

    @Override
    public void startElement(String uri, String localName, String qName, Attributes atts) 
    throws SAXException {
        super.startElement(uri, localName, qName, atts);
        
        if (qName.equals("entry")) {
            String name = atts.getValue("player_name");
            int score = Integer.valueOf(atts.getValue("score"));
            int difficulty = Integer.valueOf(atts.getValue("difficulty"));
            Date date;
            try {
                date = new SimpleDateFormat("yyyy-mm-dd kk:mm:ss").parse(atts.getValue("creation_date"));
            } catch (ParseException ex) {
                date = (Date) null; // Ensure assignment
                System.out.println("ERROR: ParseException thrown");
                System.exit(1);
            }
            this.entries.add(new HighscoreEntry(name, score, difficulty, date));
        }
    }

    public ArrayList<HighscoreEntry> getEntries() {
        return entries;
    }
    
}
