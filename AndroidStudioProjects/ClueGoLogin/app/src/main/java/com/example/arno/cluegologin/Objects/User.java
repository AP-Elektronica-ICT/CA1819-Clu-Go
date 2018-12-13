package com.example.arno.cluegologin.Objects;

import java.io.Serializable;
import java.util.List;

public class User implements Serializable {
    private int userId;
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


}
