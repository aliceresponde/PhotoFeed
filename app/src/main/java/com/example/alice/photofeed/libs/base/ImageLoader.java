package com.example.alice.photofeed.libs.base;

import android.widget.ImageView;

/**
 * Created by alice on 6/23/16.
 */

public interface ImageLoader {
    void load(ImageView imageView, String URL);
    void setOnFinishedImageLoadingListener(Object listener);//cuando se ejecute la carga
}
