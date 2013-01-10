package utils;

import model.GameConfiguration;
import org.xml.sax.helpers.DefaultHandler;

/**
 * XML Parser that recieves our configuration info 
 */
public class SaxConfigurationHandler extends DefaultHandler {

    GameConfiguration config;
}
