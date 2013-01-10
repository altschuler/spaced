package controller;

import java.net.URL;
import java.util.ArrayList;
import model.MainModel;
import model.HighscoreEntry;
import service.HighscoreService;
import view.MainView;

public class HighscoreController extends AbstractController {

    private final URL URL_GET = this.gameModel.getGameConfig().getHsGet();
    private final URL URL_ADD = this.gameModel.getGameConfig().getHsAdd();
    private final String TOKEN = this.gameModel.getGameConfig().getToken();
    
    private ArrayList<HighscoreEntry> entries;
    
    private HighscoreService service;
    
    private static final int LIMIT = 20;
    
    public HighscoreController(MainView gw, MainModel gm) {
        super(gw, gm);
        
        this.service = new HighscoreService();
        this.service.loadEntries(URL_GET, LIMIT);
        this.entries = service.getEntries();
    }
    
    public void reloadEntries() {
        this.service = new HighscoreService();
        this.service.loadEntries(URL_GET, LIMIT);
        this.entries = service.getEntries();
    }

    public ArrayList<HighscoreEntry> getEntries() {
        return entries;
    }
    
    public Boolean add(HighscoreEntry entry) {
        return this.service.addEntry(entry, URL_ADD, TOKEN);
    }
    
}
