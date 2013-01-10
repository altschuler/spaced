package service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.ArrayList;
import model.HighscoreEntry;

public class HighscoreService {
	private ArrayList<HighscoreEntry> entries;
	
	public HighscoreService() {
		this.entries = new ArrayList<HighscoreEntry>();
	}
	
	public void loadHighscores() {
		try {
			URL url = new URL("http://trololo.dk/spaced-web/get_scores?limit=20");
			BufferedReader reader = null;
	
			try {
			    reader = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"));
	
			    for (String line; (line = reader.readLine()) != null;) {
			        System.out.println(line);
			    }
			} finally {
			    if (reader != null) try { reader.close(); } catch (IOException ignore) {}
			}
		} catch (UnsupportedEncodingException e) {
			
		} catch (IOException e) {
			
		}
	}
	
	public boolean addEntry(HighscoreEntry entry) {
		//TODO implement
		return true;
	}
	
	public ArrayList<HighscoreEntry> getEntries() {
		return entries;
	}
}
