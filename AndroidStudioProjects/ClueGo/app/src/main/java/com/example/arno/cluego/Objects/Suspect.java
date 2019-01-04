package com.example.arno.cluego.Objects;

import java.io.Serializable;

public class Suspect implements Serializable {
        private int susId;
        private Boolean isMurderer;
        private String susName;
        private String susDescription;
        private String susWeapon;
        private String susImgUrl;

    public Boolean getMurderer() {
            return isMurderer;
    }

    public int getSusId() {
        return susId;
    }

    public String getSusDescription() {
        return susDescription;
    }

    public String getSusImgUrl() {
        return susImgUrl;
    }

    public String getSusName() {
        return susName;
    }

    public String getSusWeapon() {
        return susWeapon;
    }

    public void setMurderer(Boolean murderer) {
        isMurderer = murderer;
    }

    public void setSusDescription(String susDescription) {
        this.susDescription = susDescription;
    }

    public void setSusId(int susId) {
        this.susId = susId;
    }

    public void setSusImgUrl(String susImgUrl) {
        this.susImgUrl = susImgUrl;
    }

    public void setSusName(String susName) {
        this.susName = susName;
    }

    public void setSusWeapon(String susWeapon) {
        this.susWeapon = susWeapon;
    }

}
