package com.example.alice.photofeed.main.di;

import com.example.alice.photofeed.domain.di.AppModule;
import com.example.alice.photofeed.domain.di.DomainModule;
import com.example.alice.photofeed.libs.di.LibsModule;
import com.example.alice.photofeed.main.ui.MainActivity;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by alice on 7/14/16.
 * MainModule -   Presenter, adapter
 * DomainModule - FirebaseAPI
 * LibsModule - EventBus
 * AppModule - Context
 *
 */
@Singleton
@Component (modules = {MainModule.class, DomainModule.class, LibsModule.class, AppModule.class})
public interface MainComponent {

    //Tarjet
    void inject(MainActivity activity);
}
