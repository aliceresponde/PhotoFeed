package com.example.alice.photofeed.login;

import com.example.alice.photofeed.domain.FirebaseAPI;
import com.example.alice.photofeed.domain.FirebaseActionListenerCallBack;
import com.example.alice.photofeed.libs.base.EventBus;
import com.example.alice.photofeed.login.events.LoginEvent;
import com.firebase.client.FirebaseError;

/**
 * Created by alice on 6/11/16.
 */
public class LoginRepositoryImpl implements LoginRepository {

    private EventBus eventBus;
    private FirebaseAPI firebaseAPI;

    public LoginRepositoryImpl(EventBus eventBus, FirebaseAPI firebaseAPI) {
        this.eventBus = eventBus;
        this.firebaseAPI = firebaseAPI;
    }

    @Override
    public void signUp(final String email, final String password) {
        firebaseAPI.signup(email, password , new FirebaseActionListenerCallBack(){

            @Override
            public void onSuccess() {
                postEvent(LoginEvent.onSignUpError);
                signIn(email,password);
            }

            @Override
            public void onError(FirebaseError error) {
                postEvent(LoginEvent.onSignUpError, error.getMessage(), null);
            }
        });
    }

    @Override
    public void signIn(final String email, String password) {
        if (email != null && password != null){
            firebaseAPI.login(email, password, new FirebaseActionListenerCallBack() {
                @Override
                public void onSuccess() {
                    String email = firebaseAPI.getAuthEmail();
                    postEvent(LoginEvent.onSignInSuccess, null, email);
                }

                @Override
                public void onError(FirebaseError error) {
                    postEvent(LoginEvent.onSignInError, error.getMessage(), null);
                }
            });
        }else{
            firebaseAPI.checkForSesion(new FirebaseActionListenerCallBack() {
                @Override
                public void onSuccess() {
                    String email  = firebaseAPI.getAuthEmail();
                    postEvent(LoginEvent.onSignInSuccess, email);
                }

                @Override
                public void onError(FirebaseError error) {
                    postEvent(LoginEvent.onFailedRecoverSession, error.getMessage(), null);

                }
            });

        }
    }


    private void  postEvent(int type, String errorMessage, String currentUserEmail){
        LoginEvent event = new LoginEvent();
        event.setEventType(type);
        event.setErrorMessage(errorMessage);
        event.setCurrentUserEmail(currentUserEmail);
        eventBus.post(event);
    }

    private void postEvent(int type){
        postEvent(type, null, null);
    }

    private void postEvent(int type, String email){
        postEvent(type, null, email);
    }
}
