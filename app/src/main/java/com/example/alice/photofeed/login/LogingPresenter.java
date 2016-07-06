package com.example.alice.photofeed.login;


import com.example.alice.photofeed.login.events.LoginEvent;

/**
 * Created by andrearodriguez on 6/9/16.
 */
public interface LogingPresenter {
    void onCreate();
//    void onResume();
//    void onPause();
    void onDestroy();
//    void checkForAuthenticationUser();   va en login
    void validateLogin(String email, String password);
    void registerNewUser(String email, String password);
    void onEventMainThread(LoginEvent event);
}
