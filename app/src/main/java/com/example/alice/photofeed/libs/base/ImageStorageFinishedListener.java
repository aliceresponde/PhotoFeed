package com.example.alice.photofeed.libs.base;

/**
 * Created by alice on 7/5/16.
 */
public interface ImageStorageFinishedListener {
    void  onSuccess();
    void  onError(String error);
}
