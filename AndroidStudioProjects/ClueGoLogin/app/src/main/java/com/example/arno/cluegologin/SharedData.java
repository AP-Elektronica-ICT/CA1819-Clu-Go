package com.example.arno.cluegologin;

import android.app.Application;

public class SharedData extends Application {

        private String userId;
        boolean isLoggedIn;
        private static SharedData instance = new SharedData();

    public String GetUserId() {
        return userId;
    }

    public void SetUserId(String id) {
        userId = id;
    }

    public Boolean SetStatus() {
        return isLoggedIn;
    }

    public void SetStatus(boolean status) {
        isLoggedIn = status;
    }
    }

