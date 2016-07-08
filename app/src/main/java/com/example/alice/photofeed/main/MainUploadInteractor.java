package com.example.alice.photofeed.main;

import android.location.Location;

/**
 * Created by alice on 7/7/16.
 * Carga el archivo
 */
public interface MainUploadInteractor {
    void executeUploadImage(Location location, String path);
}
