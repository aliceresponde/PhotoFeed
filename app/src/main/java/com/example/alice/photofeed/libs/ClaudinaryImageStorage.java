package com.example.alice.photofeed.libs;

import android.os.AsyncTask;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.example.alice.photofeed.libs.base.ImageStorage;
import com.example.alice.photofeed.libs.base.ImageStorageFinishedListener;

import java.io.File;
import java.io.IOException;
import java.util.Map;

/**
 * Created by alice on 7/5/16.
 */

public class ClaudinaryImageStorage implements ImageStorage {
    private Cloudinary cloudinary;

    public ClaudinaryImageStorage(Cloudinary cloudinary) {
        this.cloudinary = cloudinary;
    }

    @Override
    public String getImageURL(String imageId) {
        return cloudinary.url().generate(imageId);
    }

    @Override
    public void upload(final File file, final String id, final ImageStorageFinishedListener lister) {
        new AsyncTask<Void, Void, Void>(){
            boolean success = false;
            @Override
            protected Void doInBackground(Void... voids) {
                Map params = ObjectUtils.asMap("public_id",id);
                try{
                    cloudinary.uploader().upload(file, params);
                    success = true;
                }catch (IOException e){
                    lister.onError(e.getLocalizedMessage());
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                if (success){
                    lister.onSuccess();
                }
            }
        }.execute();
    }
}
