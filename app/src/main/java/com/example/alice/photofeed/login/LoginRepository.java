package com.example.alice.photofeed.login;

/**
 * Created by Alice on 6/9/16.
 */
public interface LoginRepository {
    void signUp (String email, String password);
    void SignIn (String email, String password);
//    void checkSession();
}
