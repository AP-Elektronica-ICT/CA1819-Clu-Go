package com.example.arno.cluego.Objects;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class Game implements Serializable {
    private int gameId, userId;
    private Boolean gameWon;
    private String murderer;
    private ArrayList<Clue> clues = new ArrayList<Clue>();

    private ArrayList<Location> locations = new ArrayList<Location>();
    private ArrayList<Suspect> suspects =new ArrayList<Suspect>();

    public void setGameId(int i){
        this.gameId = i;

    }
    public int getGameId(){
        return gameId;
    }
    public void setGameWon(Boolean b){
        this.gameWon =b;
    }
    public Boolean getGameWon(){
        return gameWon;
    }
    public void setUserId(int i){
        this.userId =i;
    }
    public int getUserId(){
        return userId;
    }

    public void setClues(Clue cl) {
        this.clues.add(cl);
    }
    public void setLocations(Location loc) {

        this.locations.add(loc);
    }

    public void setSuspects(Suspect suspect) {
        this.suspects.add(suspect);
        setMurderer(suspect);
    }

    public List<Suspect> getSuspects() {
        return suspects;
    }

    public List<Location> getLocations() {
        return locations;
    }

    public void setMurderer(Suspect suspect){
           if (suspect.getMurderer())
               this.murderer = suspect.getSusName();
    }

   public String getMurderer(){
        return this.murderer;
    }
    public List<Clue> getClues() {
        return clues;
    }
}
