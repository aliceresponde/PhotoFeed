package com.example.alice.photofeed.domain;

import com.example.alice.photofeed.entities.Photo;
import com.firebase.client.AuthData;
import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.Map;

/**
 * Created by alice on 7/5/16.
 * Creo los objetos de Firebase en esta clase, y voy a utilizar los callbacks  por quien lo mande a llamar
 */

public class FirebaseAPI {
    private Firebase firebase;
    private ChildEventListener photoEventListener;

    public FirebaseAPI(Firebase firebase) {
        this.firebase = firebase;
    }

    /**
     * To  know  if  we  have data on FireBase
     * @param listenerCallBacks
     */
    public void  checkForData(final  FirebaseActionListenerCallBack listenerCallBacks){
        firebase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.getChildrenCount() > 0){
                    listenerCallBacks.onSuccess();
                }else{
                    listenerCallBacks.onError(null);
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                listenerCallBacks.onError(firebaseError);
            }
        });
    }

    /**
     *
     * @param   listenerCallBack - FirebaseEventListenerCallBack
     */
    public void  subscribe(final FirebaseEventListenerCallBack listenerCallBack){
        if (photoEventListener ==null){
            photoEventListener = new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    listenerCallBack.onChildAdded(dataSnapshot);
                }

                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String s) {}

                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot) {
                    listenerCallBack.onChildRemoved(dataSnapshot);

                }

                @Override
                public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                    listenerCallBack.onChildRemoved(dataSnapshot);
                }

                @Override
                public void onCancelled(FirebaseError firebaseError) {
                    listenerCallBack.onCancelled(firebaseError);
                }
            };
            firebase.addChildEventListener(photoEventListener);
        }
    }

    public void unSubscribe(){
        if(photoEventListener != null) {
            firebase.removeEventListener(photoEventListener);
        }
    }


    /**
     * KeyFrom Firebase, is used on cloudinary
     * @return
     */
    public String create(){
        return firebase.push().getKey();
    }

    public  void upadate(Photo photo){
        this.firebase.child(photo.getId()).setValue(photo);
    }

    public void remove(Photo photo, FirebaseActionListenerCallBack listenerCallBack){
        firebase.child(photo.getId()).removeValue();
        listenerCallBack.onSuccess();
    }

//    ===========================  Session=====================================================
    public String getAuthEmail(){
        String email  = null;
        if (firebase.getAuth() != null){
            Map<String, Object> providerData = firebase.getAuth().getProviderData();
            email = providerData.get("email").toString();
        }
        return email;
    }

    /**
     * Exist a session
     * @param listenerCallBack
     */
    public void checkForSesion(FirebaseActionListenerCallBack listenerCallBack){
        if (firebase.getAuth() != null){
            listenerCallBack.onSuccess();
        }else{
            listenerCallBack.onError(null);
        }
    }

    public void login(String email, String password , final FirebaseActionListenerCallBack listenerCallBack){
        firebase.authWithPassword(email, password, new Firebase.AuthResultHandler() {
            @Override
            public void onAuthenticated(AuthData authData) {
                listenerCallBack.onSuccess();
            }

            @Override
            public void onAuthenticationError(FirebaseError firebaseError) {
                listenerCallBack.onError(firebaseError);
            }
        });
    }

    /**
     * Create User
     * @param email
     * @param password
     * @param listenerCallBack
     */
    public void signup(String email, String password, final FirebaseActionListenerCallBack listenerCallBack){
        firebase.createUser(email, password, new Firebase.ValueResultHandler<Map<String, Object>>(){

            @Override
            public void onSuccess(Map<String, Object> stringObjectMap) {
                listenerCallBack.onSuccess();
            }

            @Override
            public void onError(FirebaseError firebaseError) {
                listenerCallBack.onError(firebaseError);
            }
        });
    }

    public void logout(){
        firebase.unauth();
    }


}
