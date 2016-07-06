package com.example.alice.photofeed.login.di;

import com.example.alice.photofeed.domain.di.DomainModule;
import com.example.alice.photofeed.domain.di.AppModule;
import com.example.alice.photofeed.libs.di.LibsModule;
import com.example.alice.photofeed.login.ui.LoginActivity;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by alice on 7/6/16.
 */
@Singleton
@Component(modules = {LoginModule.class, DomainModule.class, LibsModule.class, AppModule.class})
public interface LoginComponent {
    //target
    void  inject(LoginActivity activity);
}
