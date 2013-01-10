package model;

import java.util.Comparator;
import java.util.Date;

public class HighscoreEntry implements Comparator<HighscoreEntry> {
	
    private String playerName;
    private int score;
    private int difficulty;
    private Date creationDate;

    public HighscoreEntry(String playerName, int score, int difficulty, Date creationDate) {
        this.playerName = playerName;
        this.score = score;
        this.difficulty = difficulty;
        this.creationDate = creationDate;
    }
        
    @Override
    public int compare(HighscoreEntry hs1, HighscoreEntry hs2) {
       return (hs1.getScore() < hs2.getScore()) ? -1 : (hs1.getScore() > hs2.getScore()) ? 1 : 0;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }
}
