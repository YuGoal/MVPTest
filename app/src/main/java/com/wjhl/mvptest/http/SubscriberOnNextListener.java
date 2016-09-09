package com.wjhl.mvptest.http;

/**
 * Created by lenovo on 2016/7/27.
 */
public interface SubscriberOnNextListener<T> {

    void onNext(T t);

    void onFail();
}
