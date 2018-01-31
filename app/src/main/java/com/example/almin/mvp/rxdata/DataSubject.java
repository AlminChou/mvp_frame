package com.example.almin.mvp.rxdata;


import android.support.annotation.NonNull;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;

/**
 * Created by Almin on 2018/1/30.
 * rxjava实现的eventbus效果的订阅类，用于刷新数据，可以新建一个全局单例持有当eventbus使用，
 * 但是最好还是分模块各自一个DataSubject，减少总线的订阅量避免性能问题
 */

public class DataSubject {
    private Subject<Object> subject;

    public DataSubject(){
        subject = PublishSubject.create().toSerialized();
    }

    @SuppressWarnings("unchecked")
    public <T> Observable<EventObject<T>> join(@NonNull Class<T> clazz, @NonNull String eventName){
        return subject.ofType(EventObject.class).filter(new SubjectFilter(clazz,eventName));
    }


    public void close(){
        subject.onComplete();
    }

    public void post(EventObject eventObject) {
        if(subject.hasObservers()){
            subject.onNext(eventObject);
        }
    }


//    public static void main(String[] args) {
//        DataSubject dataSubject = new DataSubject();
////        dataSubject.join(String.class,"string").subscribe(new Consumer<EventObject<String>>() {
////            @Override
////            public void accept(EventObject<String> eventObject) throws Exception {
////                System.out.println(" accept     1111111 ");
////            }
////        });
//        dataSubject.join(Integer.class,"int").subscribe(new Consumer<EventObject<Integer>>() {
//            @Override
//            public void accept(EventObject<Integer> eventObject) throws Exception {
//                System.out.println(" accept     2222222 "+eventObject.getContent());
//            }
//        });
//
//        EventObject eventObject = EventObject.create(null,"int");
//        dataSubject.subject.onNext(eventObject);
//        eventObject.setContent(555);
//        dataSubject.subject.onNext(eventObject);
//    }

}
