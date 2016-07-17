package com.example.alice.photofeed.photolist.di;

import com.example.alice.photofeed.domain.di.AppModule;
import com.example.alice.photofeed.domain.di.DomainModule;
import com.example.alice.photofeed.libs.di.LibsModule;
import com.example.alice.photofeed.photolist.ui.PhotoListFragment;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by alice on 7/14/16.
 * PhotoListModule -   Presenter, adapter
 * DomainModule - FirebaseAPI
 * LibsModule - EventBus
 * AppModule - Context
 *
 */
@Singleton
@Component (modules = {PhotoListModule.class, DomainModule.class, LibsModule.class, AppModule.class})
public interface PhotoListComponent {

    //Tarjet
    void inject(PhotoListFragment fragment);
}
