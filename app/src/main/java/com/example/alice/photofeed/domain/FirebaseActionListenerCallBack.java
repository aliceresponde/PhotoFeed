package com.example.alice.photofeed.domain;

import com.firebase.client.FirebaseError;

/**
 * Created by alice on 7/5/16.
 * Inicio de sesion
 */

public interface FirebaseActionListenerCallBack {
    void onSuccess();
    void onError(FirebaseError error);
}
