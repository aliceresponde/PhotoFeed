package com.example.alice.photofeed.photolist;

import com.example.alice.photofeed.entities.Photo;
import com.example.alice.photofeed.libs.base.EventBus;
import com.example.alice.photofeed.photolist.events.PhotoListEvent;
import com.example.alice.photofeed.photolist.ui.PhotoListView;

import org.greenrobot.eventbus.Subscribe;

/**
 * Created by alice on 7/16/16.
 */
public class PhotoListPresenterImp implements PhotoListPresenter {
    private static final String EMPTY_LIST = "Listado vacio" ;
    private EventBus eventBus;
    private PhotoListView view;
    private PhotoListInteractor interactor;

    public PhotoListPresenterImp(EventBus eventBus, PhotoListView view, PhotoListInteractor interactor) {
        this.eventBus = eventBus;
        this.view = view;
        this.interactor = interactor;
    }

    //    =====================EventBus ======================================

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
    public void onEventMainThread(PhotoListEvent event) {
        if (view != null){
            view.hideProgress();
            view.showList();
        }

        String error = event.getError();
        if (error!=null){
            if (error.isEmpty()){
                view.onPhotoError(EMPTY_LIST);
            }else {
                view.onPhotoError(error);
            }

        } else{
            if (event.getType() == PhotoListEvent.READ_EVENT){
                view.addPhoto(event.getPhoto());
            }else if(event.getType() == PhotoListEvent.DELETE_EVENT){
                view.removePhoto(event.getPhoto());
            }
        }

    }

//    ==================FIREBASE=============================

    @Override
    public void subscribe() {
        if (view != null){
            view.hiddeList();
            view.showProgress();
        }

        interactor.subscribe();
    }

    @Override
    public void unsubscribe() {
        interactor.unsubscribe();
    }


    @Override
    public void onRemove(Photo photo) {
        interactor.onRemove(photo);
    }
}
