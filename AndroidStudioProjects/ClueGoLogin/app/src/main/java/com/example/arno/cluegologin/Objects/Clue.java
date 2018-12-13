package com.example.arno.cluegologin.Objects;

import java.io.Serializable;

public class Clue implements Serializable {
    private int clueId;
    private String clueName;
    private String clueDescription;
    private String clueImgUrl;
    private boolean found;

    public boolean isFound() {
        return found;
    }

    public int getClueId() {
        return clueId;
    }

    public String getClueDescription() {
        return clueDescription;
    }

    public String getClueImgUrl() {
        return clueImgUrl;
    }

    public String getClueName() {
        return clueName;
    }

    public void setClueDescription(String clueDescription) {
        this.clueDescription = clueDescription;
    }

    public void setClueId(int clueId) {
        this.clueId = clueId;
    }

    public void setClueImgUrl(String clueImgUrl) {
        this.clueImgUrl = clueImgUrl;
    }

    public void setClueName(String clueName) {
        this.clueName = clueName;
    }

    public void setFound(boolean found) {
        this.found = found;
    }
}
