package com.example.alice.photofeed.main;

import android.location.Location;

/**
 * Created by alice on 7/7/16.
 */
public interface MainRepository {
    void logout();
    void uploadPhoto(Location location, String path);
}
