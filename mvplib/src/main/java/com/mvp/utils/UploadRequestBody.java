package com.mvp.utils;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okio.Buffer;
import okio.BufferedSink;
import okio.ForwardingSink;
import okio.Okio;
import okio.Sink;

/**
 * 重写okHttp RequestBody
 */
public class UploadRequestBody extends RequestBody {

    /*private RequestBody requestBody;
    private UploadObserver<ResponseBody> uploadObserver;

    public UploadRequestBody(RequestBody requestBody) {
        this.requestBody = requestBody;
    }

    public UploadRequestBody(RequestBody requestBody, UploadObserver<ResponseBody> uploadObserver) {
        this.requestBody = requestBody;
        this.uploadObserver = uploadObserver;
    }

    @Override
    public MediaType contentType() {
        return requestBody.contentType();
    }


    @Override
    public long contentLength() throws IOException {
        return requestBody.contentLength();
    }

    @Override
    public void writeTo(BufferedSink sink) throws IOException {

        CountingSink countingSink = new CountingSink(sink);
        BufferedSink bufferedSink = Okio.buffer(countingSink);
        //写入
        requestBody.writeTo(bufferedSink);
        //必须调用flush，否则最后一部分数据可能不会被写入
        bufferedSink.flush();
    }

    private class CountingSink extends ForwardingSink {

        private long bytesWritten = 0;

        public CountingSink(Sink delegate) {
            super(delegate);
        }


        @Override
        public void write(Buffer source, long byteCount) throws IOException {
            super.write(source, byteCount);
            bytesWritten += byteCount;
            if (uploadObserver != null) {
                uploadObserver.onProgressChange(bytesWritten, contentLength());
            }

        }
    }*/

    //实际的待包装请求体
    private RequestBody requestBody;

    //包装完成的BufferedSink
    private BufferedSink bufferedSink;

    //private ProgressRequestListener progressRequestListener;

    /**
     * 构造函数，赋值
     *
     * @param requestBody 待包装的请求体
     */
    public UploadRequestBody(RequestBody requestBody) {
        this.requestBody = requestBody;
        //this.progressRequestListener = progressRequestListener;
    }


    /**
     * 重写调用实际的响应体的contentType
     *
     * @return MediaType
     */
    @Override
    public MediaType contentType() {
        return requestBody.contentType();
    }

    /**
     * 重写调用实际的响应体的contentLength
     *
     * @return contentLength
     * @throws IOException 异常
     */
    @Override
    public long contentLength() throws IOException {
        return requestBody.contentLength();
    }

    /**
     * 重写进行写入
     *
     * @param sink BufferedSink
     * @throws IOException 异常
     */
    @Override
    public void writeTo(BufferedSink sink) throws IOException {

        BufferedSink bufferedSink;

        mCountingSink = new CountingSink(sink);
        bufferedSink = Okio.buffer(mCountingSink);

        requestBody.writeTo(bufferedSink);
        bufferedSink.flush();


    }

    private CountingSink mCountingSink;


    /**
     * 写入，回调进度接口
     *
     * @return Sink
     */

    class CountingSink extends ForwardingSink {

        private long bytesWritten = 0;

        public CountingSink(Sink delegate) {
            super(delegate);
        }

        @Override
        public void write(Buffer source, long byteCount) throws IOException {
            super.write(source, byteCount);
            bytesWritten += byteCount;
            //progressRequestListener.onRequestProgress(bytesWritten, contentLength(), bytesWritten == contentLength());

        }
    }
}
