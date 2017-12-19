package com.example.library.listener;

/**
 * Created by almin on 2017/12/13.
 */

public interface Consumer<T> {
    void accept(T t);
}
