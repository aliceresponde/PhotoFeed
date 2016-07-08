package com.example.alice.photofeed;

import android.app.Application;

import com.example.alice.photofeed.domain.di.AppModule;
import com.example.alice.photofeed.domain.di.DomainModule;
import com.example.alice.photofeed.libs.di.LibsModule;
import com.example.alice.photofeed.login.di.DaggerLoginComponent;
import com.example.alice.photofeed.login.di.LoginComponent;
import com.example.alice.photofeed.login.di.LoginModule;
import com.example.alice.photofeed.login.ui.LoginView;
import com.firebase.client.Firebase;

/**
 * Created by alice on 7/5/16.
 */

public class App extends Application {

    private final static String EMAIL_KEY = "email";
    private final static String SHARED_PREFERENCES_NAME = "UserPrefs";
    private final static String FIREBASE_URL = "https://myphotofeededx.firebaseio.com/";


    private AppModule appModule;
    private DomainModule domainModule;

    @Override
    public void onCreate() {
        super.onCreate();
        initFirebase();
        initModules();
    }

    private void initModules() {
        appModule = new AppModule(this);
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

    // regresar componente
    public LoginComponent getLoginComponent(LoginView view){
        return DaggerLoginComponent
                .builder()
                .appModule(appModule)
                .domainModule(domainModule)
                .libsModule( new LibsModule(null))
                .loginModule( new LoginModule(view))
                .build();
    }

}
