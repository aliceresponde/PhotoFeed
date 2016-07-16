package com.example.alice.photofeed.main.ui;

/**
 * Created by alice on 7/7/16.
 */
public interface MainView {
    void onUploadInit(); //cuando se toma la foto
    void onUploadComplete(); // se publica
    void onUploadError(String error); //error
}
