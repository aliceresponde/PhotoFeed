package com.example.alice.photofeed.login;

/**
 * Created by andrearodriguez on 6/9/16.
 */
public interface LoginInteractor {
//    void checkSession();
    void doSignUp(String email, String password);
    void doSignIn(String email, String password);
}
