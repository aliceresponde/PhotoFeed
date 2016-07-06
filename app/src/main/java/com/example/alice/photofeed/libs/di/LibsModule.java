package com.example.alice.photofeed.libs.di;




import android.app.Activity;
import android.app.Fragment;
import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.cloudinary.Cloudinary;
import com.cloudinary.android.Utils;
import com.example.alice.photofeed.domain.Util;
import com.example.alice.photofeed.libs.ClaudinaryImageStorage;
import com.example.alice.photofeed.libs.GlideImageLoader;
import com.example.alice.photofeed.libs.GreenRobotsEventBus;
import com.example.alice.photofeed.libs.base.EventBus;
import com.example.alice.photofeed.libs.base.ImageLoader;
import com.example.alice.photofeed.libs.base.ImageStorage;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;


/**
 * Created by alice on 6/23/16.
 * Provee la dependencia de las librerias
 */

@Module
public class LibsModule {
    private Fragment fragment;


    public LibsModule(Fragment fragment) {
        this.fragment = fragment;
    }

    /**
     * Crea un new GreenRobotsEventBus a partir del mio
     * @param eventBus
     * @return
     */
    @Provides
    @Singleton
    EventBus providesEventBus(org.greenrobot.eventbus.EventBus eventBus ){
        return new GreenRobotsEventBus(eventBus);
    }

    /**
     * Retorna unstancia del GreenRobotEventBus
     * @return
     */
    @Provides
    @Singleton
    org.greenrobot.eventbus.EventBus providesLibraryEventBus(){
        return org.greenrobot.eventbus.EventBus.getDefault();
    }

    @Provides
    @Singleton
    ImageLoader providesImageLoader(RequestManager requestManager){
        return new GlideImageLoader(requestManager);
    }


    @Provides
    @Singleton
    RequestManager providesRequestManager(Fragment fragment){
        return Glide.with(fragment);
    }

    @Provides
    @Singleton
    Fragment providesFragment(){
        return  this.fragment;
    }

//    ======================CLoudinary ================================================================

    @Provides
    @Singleton
    ImageStorage providesImageStorage(Cloudinary cloudinary){
        return  new ClaudinaryImageStorage(cloudinary);
    }

    @Provides
    @Singleton
    Cloudinary providesCloudinary(Context context){
        return new Cloudinary(Utils.cloudinaryUrlFromContext(context));
    }



}
