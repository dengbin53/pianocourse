package com.mvp.base;

import java.io.Serializable;

public abstract class MvpModel implements Serializable {

    public abstract boolean isSuccess();

    public abstract String getMsg();

    public abstract int getCode();

}