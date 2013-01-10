package service;

import java.util.Stack;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * Abstract XML Parser for different data collections 
 */
public abstract class AbstractSaxHandler extends DefaultHandler {
    
    private Stack<String> elementStack = new Stack<>();
    
    @Override
    public void startElement(String uri, String localName, String qName, Attributes atts)
    throws SAXException {

        this.elementStack.push(qName);
        
    }

    @Override
    public void endElement(String uri, String localName, String qName)
    throws SAXException {
        this.elementStack.pop();
    }

    @Override
    public void characters(char ch[], int start, int length)
    throws SAXException {
        
    }

    private String currentElement() {
        return this.elementStack.peek();
    }

    private String currentElementParent() {
        if(this.elementStack.size() < 2) return null;
        return this.elementStack.get(this.elementStack.size()-2);
    }
    
    @Override
    public void startDocument() throws SAXException {
            
    }

    @Override
    public void endDocument() throws SAXException {
        
    }
}
