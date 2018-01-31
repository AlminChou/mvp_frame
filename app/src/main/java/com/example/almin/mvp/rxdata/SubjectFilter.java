package com.example.almin.mvp.rxdata;

import io.reactivex.functions.Predicate;

/**
 * Created by Almin on 2018/1/30.
 */
public class SubjectFilter<T> implements Predicate<EventObject> {
    private Class<T> eventType;
    private String eventName;

    public SubjectFilter(Class<T> eventType, String eventName) {
        this.eventType = eventType;
        this.eventName = eventName;
    }

    @Override
    public boolean test(EventObject eventObject) throws Exception {
        return eventObject.getEvent().equals(eventName) &&
                (eventObject.getContent()==null ||
                        eventObject.getContent().getClass().getName().equals(eventType.getName()));
    }
}