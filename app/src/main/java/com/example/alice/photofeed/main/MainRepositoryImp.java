package com.example.alice.photofeed.main;

import android.location.Location;

import com.example.alice.photofeed.domain.FirebaseAPI;
import com.example.alice.photofeed.entities.Photo;
import com.example.alice.photofeed.libs.base.EventBus;
import com.example.alice.photofeed.libs.base.ImageStorage;
import com.example.alice.photofeed.libs.base.ImageStorageFinishedListener;
import com.example.alice.photofeed.main.events.MainEvent;

import java.io.File;

/**
 * Created by alice on 7/14/16.
 * Access to
 * EventBus - EventBus
 * Cloudinary - ImageStorage
 * FireBase  -  FirebaseAPI
 */
public class MainRepositoryImp implements  MainRepository {
    private EventBus eventBus;
    private FirebaseAPI firebaseAPI;
    private ImageStorage imageStorage;

    public MainRepositoryImp(EventBus eventBus, FirebaseAPI firebaseAPI, ImageStorage imageStorage) {
        this.eventBus = eventBus;
        this.firebaseAPI = firebaseAPI;
        this.imageStorage = imageStorage;
    }

    @Override
    public void logout() {
        firebaseAPI.logout();
    }

    @Override
    public void uploadPhoto(Location location, String path) {
        final String newPhotoId = firebaseAPI.create();
        final Photo photo = new Photo();
        photo.setId(newPhotoId);
        photo.setEmail(firebaseAPI.getAuthEmail());

        if (location != null){
            photo.setLatitud(location.getLatitude());
            photo.setLongitud(location.getLongitude());
        }

        post(MainEvent.UPLOAD_INIT);

        final ImageStorageFinishedListener imageListener = new ImageStorageFinishedListener() {
            @Override
            public void onSuccess() {
                String url =  imageStorage.getImageURL(newPhotoId);
                photo.setUrl(url);
                firebaseAPI.upadate(photo);
                post(MainEvent.UPLOAD_COMPLETE);
            }

            @Override
            public void onError(String error) {
                post(MainEvent.UPLOAD_ERROR);
            }
        };

        imageStorage.upload(new File(path), newPhotoId, imageListener);
    }

    private void post(int eventType, String error) {
        MainEvent event = new MainEvent();
        event.setType(eventType);
        event.setError(error);
        eventBus.post(event);
    }


    private void post(int eventType) {
        post( eventType, null);
    }
}
