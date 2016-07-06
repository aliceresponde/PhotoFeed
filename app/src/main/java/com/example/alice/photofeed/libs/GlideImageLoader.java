package com.example.alice.photofeed.libs;

import android.widget.ImageView;

import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestListener;
import com.example.alice.photofeed.libs.base.ImageLoader;

/**
 * Created by alice on 7/3/16.
 */

public class GlideImageLoader implements ImageLoader {
    private RequestManager glideRequestManager;
    private RequestListener onFinishLoadingListener;

    public GlideImageLoader(RequestManager glideRequestManager) {
        this.glideRequestManager = glideRequestManager;
    }


    @Override
    public void load(ImageView imageView, String URL) {
        if (onFinishLoadingListener !=null){
            glideRequestManager
                    .load(URL)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .centerCrop()
                    .listener(onFinishLoadingListener)
                    .into(imageView);

        }else {
            glideRequestManager
                    .load(URL)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .centerCrop()
                    .into(imageView);
        }
    }

    @Override
    public void setOnFinishedImageLoadingListener(Object listener) {
            if (listener instanceof  RequestListener ){
                this.onFinishLoadingListener = (RequestListener) listener;
            }
    }
}
