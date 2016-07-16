package com.example.alice.photofeed.main;

import android.location.Location;

/**
 * Created by alice on 7/7/16.
 */
public interface MainRepository {

//    ===============Sesion============================
    void logout();

//    ==============UploadPhoto=========================
    void uploadPhoto(Location location, String path);
}
