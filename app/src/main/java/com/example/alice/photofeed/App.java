package com.example.alice.photofeed;

import android.app.Application;
import android.content.SharedPreferences;

import com.example.alice.photofeed.domain.di.DomainModule;
import com.firebase.client.Firebase;

/**
 * Created by alice on 7/5/16.
 */

public class App extends Application {

    private final static String EMAIL_KEY = "email";
    private final static String SHARED_PREFERENCES_NAME = "UserPrefs";
    private final static String FIREBASE_URL = "https://photofeed-413db.firebaseio.com/";


    private PhotoFeedAppModule photoModule;
    private DomainModule domainModule;

    @Override
    public void onCreate() {
        super.onCreate();
        initFirebase();
        initModules();
    }

    private void initModules() {
        photoModule = new PhotoFeedAppModule(this);
        domainModule = new DomainModule(FIREBASE_URL);
    }

    private void initFirebase() {
        Firebase.setAndroidContext(this);
    
    }


    public  String getEmailKey() {
        return EMAIL_KEY;
    }

    public  String getSharedPreferencesName() {
        return SHARED_PREFERENCES_NAME;
    }

    public  String getFirebaseUrl() {
        return FIREBASE_URL;
    }


}
