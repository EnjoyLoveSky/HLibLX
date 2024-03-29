package com.hq.hlibrary.net.rx.down;

import okhttp3.Interceptor;
import okhttp3.Response;

import java.io.IOException;

/**
 * @author ruowuming
 * @version 1.0
 * @date 2018/4/7  15:26
 */

public class GZipInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        return chain.proceed(chain.request()
                .newBuilder()
                .addHeader("Accept-Encoding", "identity")
                .build());
    }
}
