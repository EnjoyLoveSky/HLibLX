package com.hq.hlibrary.net.rx.down;

import okhttp3.Interceptor;
import okhttp3.Response;

import java.io.IOException;

/**
 * @author ruowuming
 * @version 1.0
 * @date 2018/4/7  9:43
 */
public class DownloadInterceptor implements Interceptor {

    private DownProgressListener progressListener;

    public DownloadInterceptor(DownProgressListener listener) {
        this.progressListener = listener;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Response response=chain.proceed(chain.request());
        return response.newBuilder()
                .body(new ProgressResponseBody(response.body(),progressListener))
                .build();
    }
}
