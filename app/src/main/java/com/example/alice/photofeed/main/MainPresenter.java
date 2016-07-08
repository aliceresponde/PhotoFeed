package com.example.alice.photofeed.main;

import android.location.Location;

import com.example.alice.photofeed.main.events.MainEvent;

/**
 * Created by alice on 7/7/16.
 */
public interface MainPresenter {
    void onCreate();
    void onDestroy();

    void logout();
    void uploadPhoto(Location location, String path);
    void onEventMainThread(MainEvent event);

}
