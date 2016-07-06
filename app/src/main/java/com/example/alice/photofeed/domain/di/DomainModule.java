package com.example.alice.photofeed.domain.di;

import android.content.Context;
import android.location.Geocoder;

import com.example.alice.photofeed.domain.FirebaseAPI;
import com.example.alice.photofeed.domain.Util;
import com.firebase.client.Firebase;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by alice on 7/5/16.
 *
 */
@Module
public class DomainModule {

    String firebaseURL ;

    public DomainModule(String firebaseURL) {
        this.firebaseURL = firebaseURL;
    }

//    ===================================FIREBASE ===================================

    @Provides
    @Singleton
    String providesFirebaseURL(){
        return firebaseURL;
    }

    @Provides
    @Singleton
    Firebase providesFirebase(String firebaseURL){
        return  new Firebase(firebaseURL);
    }

    @Provides
    @Singleton
    FirebaseAPI providesFirebaseAPI(Firebase firebase){
        return  new FirebaseAPI(firebase);
    }

//    =============================UTIL================================================

    @Provides
    @Singleton
    Util providesUtil(Geocoder geocoder){
        return  new Util(geocoder);
    }

    @Provides
    @Singleton
    Geocoder providesGeocoder(Context context){
        return  new Geocoder(context);
    }
}
