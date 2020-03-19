package com.mvp.observer;

import com.mvp.exception.ApiException;
import com.mvp.exception.ExceptionEngine;

import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DefaultObserver;

/**
 * 上传文件被观察者
 */
public abstract class UploadObserver<T> extends DefaultObserver<T> {

    /**
     * 监听进度变化
     *
     * @param bytesWritten  写入的长度
     * @param contentLength 总长度
     */
    public void onProgressChange(long bytesWritten, long contentLength) {
        onProgress((int) (bytesWritten * 100 / contentLength));
    }

    //上传进度回调
    public abstract void onProgress(int progress);


    @Override
    public void onNext(T t) {
        onSuccess(t);
    }

    @Override
    public void onError(Throwable e) {
        if (e instanceof ApiException) {
            onError((ApiException) e);
        } else {
            onError(new ApiException(e, ExceptionEngine.UN_KNOWN_ERROR));
        }
    }

    @Override
    public void onComplete() {

    }

    protected abstract void onStart(Disposable d);


    protected abstract void onError(ApiException e);


    protected abstract void onSuccess(T response);
    
}
