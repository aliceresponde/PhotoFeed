package com.example.alice.photofeed.photolist.ui;

import com.example.alice.photofeed.entities.Photo;

/**
 * Created by alice on 7/16/16.
 *
 */
public interface PhotoListView {
    void showList();
    void hiddeList();
    void showProgress();
    void hideProgress();


//    ======================Photo====================================
    void addPhoto(Photo photo);
    void removePhoto(Photo photo);
    void onPhotoError(String error);

}
