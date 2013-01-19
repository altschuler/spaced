package controller;

import java.net.URL;
import java.util.ArrayList;
import model.MainModel;
import model.HighscoreEntry;
import service.HighscoreService;
import view.MainView;

/**
 * Contains the highscores
 */
public class HighscoreController extends AbstractController {

	private final URL URL_GET = this.gameModel.getGameConfig().getHsGet();
	private final URL URL_ADD = this.gameModel.getGameConfig().getHsAdd();
	private final String TOKEN = this.gameModel.getGameConfig().getToken();

	private ArrayList<HighscoreEntry> entries;

	private HighscoreService service;

	private static final int LIMIT = 20;

	public HighscoreController(MainView mv, MainModel gm) {
		super(mv, gm);
		this.entries = new ArrayList<HighscoreEntry>();
		this.service = new HighscoreService();
		this.loadEntries();
	}

	public void loadEntries() {
		this.service.loadEntries(URL_GET, LIMIT);
		this.entries = service.getEntries();
		this.gameModel.setOfflineMode(this.service.isOfflineMode());
	}

	public void reloadEntries() {
		this.service = new HighscoreService();
		this.loadEntries();
	}

	public HighscoreEntry[] getEntries() {
		return entries.toArray(new HighscoreEntry[entries.size()]);
	}

	public Boolean add(HighscoreEntry entry) {
		this.entries.add(entry);
		boolean status = this.service.addEntry(entry, URL_ADD, TOKEN);
		this.gameModel.setOfflineMode(this.service.isOfflineMode());
		return status;
	}

}
