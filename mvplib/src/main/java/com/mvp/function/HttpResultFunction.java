package com.mvp.function;

import com.mvp.exception.ExceptionEngine;

import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;


/**
 * http结果处理函数
 */
public class HttpResultFunction<T> implements Function<Throwable, Observable<T>> {
    @Override
    public Observable<T> apply(@NonNull Throwable throwable) {
        //打印具体错误
        //Logger.d("错误信息"+throwable);
        return Observable.error(ExceptionEngine.handleException(throwable));
    }
}
