package com.example.almin.mvp.rxdata;

import android.support.annotation.NonNull;

/**
 * Created by Almin on 2018/1/30.
 * 通知订阅的event类
 */
public class EventObject<T> {
    private T t;
    private Class<T> clazz;
    private String event;

    public static <T> EventObject<T> create(T t , @NonNull String event){
        EventObject<T> eventObject = new EventObject<>();
        eventObject.setEvent(event);
        eventObject.setContent(t);
        return eventObject;
    }

    public T getContent() {
        return t;
    }

    public void setContent(T t) {
        this.t = t;
    }

    public Class<T> getContentClass() {
        return clazz;
    }

    public void setContentClass(Class<T> clazz) {
        this.clazz = clazz;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }
}