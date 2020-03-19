package com.mvp.function;

import com.mvp.base.MvpModel;
import com.mvp.exception.ServerException;

import io.reactivex.functions.Function;

/**
 * 服务器结果处理函数
 */
public class ServerResultFunction<T extends MvpModel> implements Function<T, T> {

    @Override
    public T apply(T t) {
        if (!t.isSuccess()) {
            throw new ServerException(t.getCode(), t.getMsg());
        }
        return t;
    }
}
