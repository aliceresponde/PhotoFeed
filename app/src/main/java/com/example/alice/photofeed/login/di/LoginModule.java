package com.example.alice.photofeed.login.di;

import com.example.alice.photofeed.domain.FirebaseAPI;
import com.example.alice.photofeed.libs.base.EventBus;
import com.example.alice.photofeed.login.LoginInteractor;
import com.example.alice.photofeed.login.LoginInteractorImpl;
import com.example.alice.photofeed.login.LoginPresenterImpl;
import com.example.alice.photofeed.login.LoginRepository;
import com.example.alice.photofeed.login.LoginRepositoryImpl;
import com.example.alice.photofeed.login.LogingPresenter;
import com.example.alice.photofeed.login.ui.LoginView;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by alice on 7/5/16.
 */
@Module
public class LoginModule {
    LoginView view;

    public LoginModule(LoginView view) {
        this.view = view;
    }

    @Provides
    @Singleton
    LoginView providesLoginView(){
        return  this.view;
    }

//    ==================================PRESENTER =================================================
    @Provides
    @Singleton
    LogingPresenter providesLogingPresenter(EventBus eventBus, LoginView loginView, LoginInteractor loginInteractor){
        return  new LoginPresenterImpl(eventBus, loginView, loginInteractor);
    }

    @Provides
    @Singleton
    LoginInteractor providesLoginInteractor(LoginRepository repository){
        return  new LoginInteractorImpl(repository);
    }

    @Provides
    @Singleton
    LoginRepository providesLoginRepository(EventBus eventBus, FirebaseAPI firebaseAPI){
        return new LoginRepositoryImpl(eventBus, firebaseAPI);
    } 



}
