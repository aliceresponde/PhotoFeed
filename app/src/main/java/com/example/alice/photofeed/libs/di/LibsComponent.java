package com.example.alice.photofeed.libs.di;

import com.example.alice.photofeed.domain.di.AppModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by alice on 7/5/16.
 */

@Singleton
@Component(modules = {LibsModule.class , AppModule.class})
public interface LibsComponent {
}
