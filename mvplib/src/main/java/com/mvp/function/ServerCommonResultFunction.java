package com.mvp.function;

import io.reactivex.functions.Function;

/**
 * 作者：RR 邮箱：RR@chengantech.com
 * 时间：2018/4/25  上午10:10
 * 描述：
 */

public class ServerCommonResultFunction<T> implements Function<T, T> {
    @Override
    public T apply(T t) throws Exception {
        return t;
    }
}
