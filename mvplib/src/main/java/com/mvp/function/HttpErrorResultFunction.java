package com.mvp.function;

import com.mvp.exception.ExceptionEngine;

import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;


/**
 * http结果处理函数
 */
public class HttpErrorResultFunction<T> implements Function<Throwable, Observable<T>> {
    @Override
    public Observable<T> apply(@NonNull Throwable throwable) {
        return Observable.error(ExceptionEngine.handleException(throwable));
    }
}
