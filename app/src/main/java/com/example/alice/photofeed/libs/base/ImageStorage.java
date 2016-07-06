package com.example.alice.photofeed.libs.base;

import java.io.File;

/**
 * Created by alice on 7/5/16.
 */

public interface ImageStorage {
    String getImageURL(String imageId);
    void upload(File file, String id, ImageStorageFinishedListener lister);
}
