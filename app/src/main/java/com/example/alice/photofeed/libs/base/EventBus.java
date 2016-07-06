package com.example.alice.photofeed.libs.base;


/**
 * Created by alice on 6/23/16.
 */

public interface EventBus {
    void register(Object subscriber);
    void unregister(Object subscriber);
    void post(Object event);
}
