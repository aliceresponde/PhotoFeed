package com.example.alice.photofeed.photolist;

import com.example.alice.photofeed.entities.Photo;

/**
 * Created by alice on 7/16/16.
 */
public class PhotoListInteractorImp implements  PhotoListInteractor {
    PhotolistRepository repository;

    public PhotoListInteractorImp(PhotolistRepository repository) {
        this.repository = repository;
    }

    @Override
    public void subscribe() {
        repository.subscribe();
    }

    @Override
    public void unsubscribe() {
        repository.unsubscribe();
    }

    @Override
    public void onRemove(Photo photo) {
        repository.onRemove(photo);
    }
}
