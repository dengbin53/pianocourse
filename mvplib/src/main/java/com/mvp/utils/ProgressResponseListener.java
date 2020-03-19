package com.mvp.utils;

/**
 *
 */
public interface ProgressResponseListener {

    void onResponseProgress(long bytesRead, long contentLength, boolean done);

}
