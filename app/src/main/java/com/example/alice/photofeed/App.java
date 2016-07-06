package com.example.alice.photofeed;

import android.app.Application;

/**
 * Created by alice on 7/5/16.
 */

public class App extends Application {

    private final static String EMAIL_KEY = "email";
    private final static String SHARED_PREFERENCES_NAME = "UserPrefs";
    private final static String FIREBASE_URL = "https://photofeed-413db.firebaseio.com/";


    @Override
    public void onCreate() {
        super.onCreate();
        initFirebase();
    }

    private void initFirebase() {
    }
}
