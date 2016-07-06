package com.example.alice.photofeed.domain.di;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by alice on 7/5/16.
 */

@Singleton
@Component(modules = {DomainModule.class, AppModule.class})
public interface DomainComponent {
}
