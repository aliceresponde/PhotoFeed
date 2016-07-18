package com.example.alice.photofeed.photolist;

import com.example.alice.photofeed.domain.FirebaseAPI;
import com.example.alice.photofeed.domain.FirebaseActionListenerCallBack;
import com.example.alice.photofeed.domain.FirebaseEventListenerCallBack;
import com.example.alice.photofeed.entities.Photo;
import com.example.alice.photofeed.libs.base.EventBus;
import com.example.alice.photofeed.photolist.events.PhotoListEvent;
import com.firebase.client.DataSnapshot;
import com.firebase.client.FirebaseError;

/**
 * Created by alice on 7/16/16.
 */
public class PhotolistRepositoryImp implements  PhotolistRepository {
    private EventBus eventBus;
    private FirebaseAPI firebaseAPI;

    public PhotolistRepositoryImp(EventBus eventBus, FirebaseAPI firebaseAPI) {
        this.eventBus = eventBus;
        this.firebaseAPI = firebaseAPI;
    }

    @Override
    public void subscribe() {
        firebaseAPI.checkForData(new FirebaseActionListenerCallBack() {
            @Override
            public void onSuccess() {
            }

            @Override
            public void onError(FirebaseError error) {
                if (error != null){
                    post(PhotoListEvent.READ_EVENT, error.getMessage());
                }else{
                    post(PhotoListEvent.READ_EVENT, ""); //no data
                }
            }
        });

        firebaseAPI.subscribe(new FirebaseEventListenerCallBack() {
            @Override
            public void onChildAdded(DataSnapshot snapshot) {
                Photo photo = snapshot.getValue(Photo.class);
                photo.setId(snapshot.getKey());

                String emial = firebaseAPI.getAuthEmail();
                boolean publishedByMe = photo.getEmail().equals(emial);
                photo.setPublishedByMe(publishedByMe);
                post(PhotoListEvent.READ_EVENT, photo);
            }

            @Override
            public void onChildRemoved(DataSnapshot snapshot) {
                Photo photo = snapshot.getValue(Photo.class);
                photo.setId(snapshot.getKey());
                post(PhotoListEvent.DELETE_EVENT, photo);
            }

            @Override
            public void onCancelled(FirebaseError error) {
                post(PhotoListEvent.READ_EVENT, error.getMessage());
            }
        });
    }

    @Override
    public void unsubscribe() {
        firebaseAPI.unSubscribe();
    }

    @Override
    public void onRemove(final Photo photo) {
        firebaseAPI.remove(photo, new FirebaseActionListenerCallBack() {
            @Override
            public void onSuccess() {
                post(PhotoListEvent.DELETE_EVENT, photo);
            }

            @Override
            public void onError(FirebaseError error) {
                post(PhotoListEvent.DELETE_EVENT, error.getMessage());
            }
        });

    }

    private void post(int eventType, String error , Photo photo) {
        PhotoListEvent event = new PhotoListEvent();
        event.setPhoto(photo);
        event.setType(eventType);
        event.setError(error);
        eventBus.post(event);
    }

    private void post(int eventType, String error) {
        post(eventType, error, null);
    }

    private void post(int eventType, Photo photo) {
        post(eventType, null, photo);
    }
}
