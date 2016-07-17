package com.example.alice.photofeed.photolist.di;


import com.example.alice.photofeed.domain.FirebaseAPI;
import com.example.alice.photofeed.domain.Util;
import com.example.alice.photofeed.entities.Photo;
import com.example.alice.photofeed.libs.base.EventBus;
import com.example.alice.photofeed.libs.base.ImageLoader;
import com.example.alice.photofeed.photolist.PhotoListInteractor;
import com.example.alice.photofeed.photolist.PhotoListInteractorImp;
import com.example.alice.photofeed.photolist.PhotoListPresenter;
import com.example.alice.photofeed.photolist.PhotoListPresenterImp;
import com.example.alice.photofeed.photolist.PhotolistRepository;
import com.example.alice.photofeed.photolist.PhotolistRepositoryImp;
import com.example.alice.photofeed.photolist.ui.PhotoListView;
import com.example.alice.photofeed.photolist.ui.adapters.OnItemClickListener;
import com.example.alice.photofeed.photolist.ui.adapters.PhotoListAdapter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by alice on 7/14/16.
 * presenter
 * adapter
 */
@Module
public class PhotoListModule {
    private PhotoListView view;
    private OnItemClickListener onItemClickListener;

    public PhotoListModule(PhotoListView view, OnItemClickListener onItemClickListener) {
        this.view = view;
        this.onItemClickListener = onItemClickListener;
    }


    // =============================PhotoListPresenter==============================================
    @Provides
    @Singleton
    PhotoListPresenter providesPhotoListPresenter(EventBus eventBus, PhotoListView view, PhotoListInteractor interactor) {
        return new PhotoListPresenterImp(eventBus, view, interactor);
    }

    @Provides
    @Singleton
    PhotoListView providesPhotoListView() {
        return view;
    }

    @Provides
    @Singleton
    PhotoListInteractor  providesPhotoListInteractor(PhotolistRepository repository){
        return  new PhotoListInteractorImp(repository);
    }

    @Provides
    @Singleton
    PhotolistRepository providesPhotolistRepository(EventBus eventBus, FirebaseAPI firebaseAPI){
        return  new PhotolistRepositoryImp(eventBus, firebaseAPI);
    }

    //    ========================  PhotoListAdapter   ==================================================
    @Provides
    @Singleton
    PhotoListAdapter providesPhotoListAdapter(Util util, List<Photo> photoList, ImageLoader imageLoader, OnItemClickListener onItemClickListener){
        return  new PhotoListAdapter(util , photoList, imageLoader , onItemClickListener);
    }

    @Provides
    @Singleton
    List<Photo> providesPhotoList(){
        return new ArrayList<Photo>();
    }

    @Provides
    @Singleton
    OnItemClickListener providesOnItemClickListener(){
        return this.onItemClickListener;
    }


}
