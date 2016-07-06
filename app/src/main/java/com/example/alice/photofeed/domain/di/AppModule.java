package com.example.alice.photofeed.domain.di;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.alice.photofeed.App;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by alice on 7/5/16.
 */
@Module
public class AppModule {

    App app;

    public AppModule(App app) {
        this.app = app;
    }


    @Provides @Singleton
    Context providesApplicationContext(){
        return app.getApplicationContext();
    }


    @Provides @Singleton App providesApp(){
        return app;
    }

    @Provides @Singleton
    SharedPreferences providesSharedPreferences(){
        return app.getSharedPreferences(app.getSharedPreferencesName(), Context.MODE_PRIVATE);
    }
}
