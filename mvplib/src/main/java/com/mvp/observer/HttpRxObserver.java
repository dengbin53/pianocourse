package com.mvp.observer;

import com.mvp.base.MvpModel;
import com.mvp.exception.ApiException;
import com.mvp.exception.ExceptionEngine;
import com.mvp.utils.HttpRequestListener;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

public abstract class HttpRxObserver<T extends MvpModel> implements Observer<T>, HttpRequestListener {

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

    @Override
    public void onNext(T tHttpResult) {
        if (tHttpResult.isSuccess()) {
            onSuccess(tHttpResult);
            return;
        }

        onError(new ApiException(tHttpResult.getCode(), tHttpResult.getMsg()));
    }

    @Override
    public void onSubscribe(@NonNull Disposable d) {
        onStart(d);
    }

    @Override
    public void cancel() {
    }

    protected void onStart(Disposable d) {
    }

    protected abstract void onError(ApiException e);

    protected abstract void onSuccess(T response);
}
