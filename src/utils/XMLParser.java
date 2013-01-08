package utils;

import java.io.FileInputStream;
import java.io.InputStream;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.parsers.SAXParser;
import org.xml.sax.helpers.DefaultHandler;

public class XMLParser {
    
    SAXParserFactory factory = SAXParserFactory.newInstance();
    
    try {

        InputStream xmlInput = new FileInputStream("theFile.xml");
        SAXParser saxParser = factory.newSAXParser();

        DefaultHandler handler = new SaxHandler();
        saxParser.parse(xmlInput, handler);

    } catch (Throwable err) {
        err.printStackTrace ();
    }

    
}
