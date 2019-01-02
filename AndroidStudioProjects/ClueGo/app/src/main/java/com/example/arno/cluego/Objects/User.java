package com.example.arno.cluego.Objects;

import java.io.Serializable;
import java.util.List;

public class User implements Serializable {
    private int userId;
    private int gamesPlayed;
    private int cluesFound;
    private int distanceWalked;
    private String username;
    private String password;
    private String email;
    private List<Game> games;


    public void setUserId(int i){
       this.userId = i;

    }
    public int getUserId(){
        return userId;
    }
    public void setUsername(String i){
        this.username=i;

    }
    public String getUsername(){
        return username;
    }
    public void setEmail(String i){
        this.email =i;
    }
    public String getEmail( ){
        return email;
    }
    public void setGames(List<Game> g){
        this.games = g;
    }
    public List<Game> getGames(){
        return games;
    }

    public int getGamesPlayed() {
        return gamesPlayed;
    }

    public int getCluesFound() {
        return cluesFound;
    }

    public int getDistanceWalked() {
        return distanceWalked;
    }

    public void setCluesFound(int cluesFound) {

        this.cluesFound = cluesFound;
    }

    public void setDistanceWalked(int distanceWalked) {
        this.distanceWalked = distanceWalked;
    }

    public void setGamesPlayed(int gamesPlayed) {
        this.gamesPlayed = gamesPlayed;
    }
}
