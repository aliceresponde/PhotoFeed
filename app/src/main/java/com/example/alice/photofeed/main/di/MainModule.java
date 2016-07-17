package com.example.alice.photofeed.main.di;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.example.alice.photofeed.domain.FirebaseAPI;
import com.example.alice.photofeed.libs.base.EventBus;
import com.example.alice.photofeed.libs.base.ImageStorage;
import com.example.alice.photofeed.main.MainPresenter;
import com.example.alice.photofeed.main.MainPresenterImp;
import com.example.alice.photofeed.main.MainRepository;
import com.example.alice.photofeed.main.MainRepositoryImp;
import com.example.alice.photofeed.main.SesionInteractor;
import com.example.alice.photofeed.main.SesionInteractorImp;
import com.example.alice.photofeed.main.UploadInteractor;
import com.example.alice.photofeed.main.UploadInteractorImp;
import com.example.alice.photofeed.main.adapters.MainSectionsPagerAdapter;
import com.example.alice.photofeed.main.ui.MainView;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by alice on 7/14/16.
 * presenter
 * adapter
 */
@Module
public class MainModule {
    private MainView view;
    private String[] titles;
    private Fragment[] fragments;
    private FragmentManager fragmentManager;


    public MainModule(MainView view, String[] titles, Fragment[] fragments, FragmentManager fragmentManager) {
        this.view = view;
        this.titles = titles;
        this.fragments = fragments;
        this.fragmentManager = fragmentManager;
    }


    @Provides
    @Singleton
    MainView providesMainView(){
        return  view;
    }

// =============================MainPresenter=======================================================
    @Provides
    @Singleton
    MainPresenter providesMainPresenter(MainView view, EventBus eventBus, UploadInteractor uploadInteractor, SesionInteractor sesionInteractor){
        return new MainPresenterImp(view, eventBus, uploadInteractor, sesionInteractor);
    }

    @Provides
    @Singleton
    UploadInteractor providesUploadInteractor(MainRepository repository){
        return  new UploadInteractorImp( repository );
    }

    @Provides
    @Singleton
    SesionInteractor providesSesionInteractor(MainRepository repository){
        return  new SesionInteractorImp(repository);
    }

    @Provides
    @Singleton
    MainRepository providesMainRepository(EventBus eventBus, FirebaseAPI firebaseAPI, ImageStorage imageStorage){
        return  new MainRepositoryImp(eventBus, firebaseAPI, imageStorage);
    }

//    ================================MainSectionsPagerAdapter======================================
    @Provides
    @Singleton
    MainSectionsPagerAdapter providesMainSectionsPagerAdapter(android.support.v4.app.FragmentManager fm, String[] titles, android.support.v4.app.Fragment[] fragments){
        return  new MainSectionsPagerAdapter(fm, titles, fragments);
    }

    @Provides
    @Singleton
    FragmentManager providesFragmentManager(){
        return  fragmentManager;
    }

    @Provides
    @Singleton
    Fragment[] providesFragmentArrayForAdapter(){
        return fragments;
    }

    @Provides
    @Singleton
    String[] providesStringArrayForAdapter(){
        return titles;
    }

}
