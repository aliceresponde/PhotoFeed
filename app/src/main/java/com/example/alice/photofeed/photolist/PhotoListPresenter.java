package com.example.alice.photofeed.photolist;

import com.example.alice.photofeed.entities.Photo;
import com.example.alice.photofeed.photolist.events.PhotoListEvent;

/**
 * Created by alice on 7/16/16.
 */
public interface PhotoListPresenter {

//    ==============================EventBus =======================================
    void  onCreate();
    void  onDestroy();
    void  onEventMainThread(PhotoListEvent event);

//    ============FIREBASE ==========================================================
    void  subscribe();
    void  unsubscribe();


//    ============================= Photo=============================================
   void  onRemove(Photo photo);




}
