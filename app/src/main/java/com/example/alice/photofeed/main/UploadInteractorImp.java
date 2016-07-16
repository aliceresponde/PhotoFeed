package com.example.alice.photofeed.main;

import android.location.Location;

/**
 * Created by alice on 7/14/16.
 */
public class UploadInteractorImp implements UploadInteractor {
    MainRepository repository;

    public UploadInteractorImp(MainRepository repository) {
        this.repository = repository;
    }

    @Override
    public void executeUploadImage(Location location, String path) {
        repository.uploadPhoto(location, path);
    }
}
