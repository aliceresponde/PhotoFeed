package com.example.alice.photofeed.photolist;

import com.example.alice.photofeed.entities.Photo;

/**
 * Created by alice on 7/16/16.
 */
public interface PhotoListInteractor {

    //    ============FIREBASE ==========================================================
    void  subscribe();
    void  unsubscribe();


    //    ============================= Photo=============================================
    void  onRemove(Photo photo);

}
