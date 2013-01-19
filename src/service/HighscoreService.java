package service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.ArrayList;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import model.HighscoreEntry;
import org.xml.sax.InputSource;

import service.parsing.HighscoreSaxHandler;

public class HighscoreService {

	private ArrayList<HighscoreEntry> entries;
	private boolean offlineMode;

	public HighscoreService() {
		this.entries = new ArrayList<HighscoreEntry>();
	}

	public void loadEntries(URL urlGet, int limit) {
		String s = "";
		URL url;

		try {
			url = new URL(urlGet.toString() + "/awd?limit=" + limit);
			BufferedReader reader = null;

			try {
				reader = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"));

				for (String line; (line = reader.readLine()) != null;) {
					s += line;
				}
			} finally {
				if (reader != null)
					try {
						reader.close();
					} catch (IOException ignore) {
					}
			}

			this.entries = this.parseXML(s);
		} catch (Exception e) {
			this.setOfflineMode(true);
		}
	}

	public boolean addEntry(HighscoreEntry entry, URL urlAdd, String token) {
		String s = "";
		URL url;

		try {
			url = new URL(urlAdd.toString() + "?token=" + token + "&player_name=" + entry.getPlayerName() + "&score=" + entry.getScore() + "&difficulty="
					+ entry.getDifficulty());
			BufferedReader reader = null;

			try {
				reader = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"));

				for (String line; (line = reader.readLine()) != null;) {
					s += line;
				}
			} finally {
				if (reader != null)
					try {
						reader.close();
					} catch (IOException ignore) {
					}
			}
			return s.equals("<status>ok</status>");
			
		} catch (Exception e) {
			this.setOfflineMode(true);
		}
		
		return false;
	}

	public ArrayList<HighscoreEntry> parseXML(String s) {
		SAXParserFactory factory = SAXParserFactory.newInstance();

		try {
			SAXParser saxParser = factory.newSAXParser();

			HighscoreSaxHandler handler = new HighscoreSaxHandler();
			saxParser.parse(new InputSource(new StringReader(s)), handler);

			return handler.getEntries();

		} catch (Exception e) {
			System.out.println(e);
		}

		return null;
	}

	public ArrayList<HighscoreEntry> getEntries() {
		return entries;
	}

	public boolean isOfflineMode() {
		return offlineMode;
	}

	public void setOfflineMode(boolean offlineMode) {
		this.offlineMode = offlineMode;
	}
}
