package com.hq.hlibrary.net.rx.upload;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

/**
 * @author ruowuming
 * @version 1.0
 * @date 2018/4/28  17:44
 */

public class UploadInterceptor implements Interceptor {
    private UploadProgressListener mUploadListener;

    public UploadInterceptor(UploadProgressListener uploadListener) {
        mUploadListener = uploadListener;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        if(null == request.body()){
            return chain.proceed(request);
        }

        Request build = request.newBuilder()
                .method(request.method(),
                        new ProgressRequestBody(request.body(),
                                mUploadListener))
                .build();
        return chain.proceed(build);
    }
}
