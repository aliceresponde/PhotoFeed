package com.example.alice.photofeed.libs.di;

import com.example.alice.photofeed.domain.di.DomainModule;
import com.example.alice.photofeed.domain.di.PhotoFeedAppModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by alice on 7/5/16.
 */

@Singleton
@Component(modules = {LibsModule.class , PhotoFeedAppModule.class})
public interface LibsComponent {
}
