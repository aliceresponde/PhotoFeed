package com.example.alice.photofeed.main;

/**
 * Created by alice on 7/14/16.
 */
public class SesionInteractorImp implements  SesionInteractor {
    MainRepository repository;

    public SesionInteractorImp(MainRepository repository) {
        this.repository = repository;
    }

    @Override
    public void logout() {
        repository.logout();
    }
}
