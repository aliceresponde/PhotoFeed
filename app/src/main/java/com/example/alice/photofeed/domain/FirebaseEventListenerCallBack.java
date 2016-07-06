package com.example.alice.photofeed.domain;

import com.firebase.client.DataSnapshot;
import com.firebase.client.FirebaseError;

/**
 * Created by alice on 7/5/16.
 */

public interface FirebaseEventListenerCallBack {
    void  onChildAdded(DataSnapshot snapshot);
    void  onChildRemoved(DataSnapshot snapshot);
    void  onCancelled(FirebaseError error);

}
