package com.example.alice.photofeed.main;

import android.location.Location;
import android.util.Log;

import com.example.alice.photofeed.libs.base.EventBus;
import com.example.alice.photofeed.main.events.MainEvent;
import com.example.alice.photofeed.main.ui.MainView;

import org.greenrobot.eventbus.Subscribe;

/**
 * Created by alice on 7/14/16.
 */
public class MainPresenterImp implements  MainPresenter {
     private MainView view;
     private EventBus eventBus;
     private UploadInteractor uploadInteractor;
     private SesionInteractor sesionInteractor;

    public MainPresenterImp(MainView view, EventBus eventBus, UploadInteractor uploadInteractor, SesionInteractor sesionInteractor) {
        this.view = view;
        this.eventBus = eventBus;
        this.uploadInteractor = uploadInteractor;
        this.sesionInteractor = sesionInteractor;
    }

    @Override
    public void onCreate() {
        eventBus.register(this);
    }

    @Override
    public void onDestroy() {
        view = null;
        eventBus.unregister(this);
    }

    @Subscribe
    @Override
    public void onEventMainThread(MainEvent event) {
        Log.d("MainPresenterImp", "EventType " + event.getType());
        if (view != null){
            switch (event.getType()){
                case  MainEvent.UPLOAD_INIT:
                    Log.d("MainPresenterImp", "onEventMainThread  UPLOAD_INIT");
                    view.onUploadInit();
                    break;

                case MainEvent.UPLOAD_COMPLETE:
                    Log.d("MainPresenterImp", "onEventMainThread  UPLOAD_COMPLETE");
                    view.onUploadComplete();
                    break;

                case MainEvent.UPLOAD_ERROR:
                    Log.d("MainPresenterImp", "onEventMainThread  UPLOAD_ERROR");
                    String error = event.getError();
                    view.onUploadError(error);
                    break;
            }
        }
    }

    @Override
    public void logout() {
        sesionInteractor.logout();
    }

    @Override
    public void uploadPhoto(Location location, String path) {
        Log.i("MainPresenterImp","uploadPhoto"  + " laoc " + location.toString() + "path: " +path);
        uploadInteractor.executeUploadImage(location, path);
    }
}
