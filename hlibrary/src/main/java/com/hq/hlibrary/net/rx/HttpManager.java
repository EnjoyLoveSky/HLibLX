package com.hq.hlibrary.net.rx;

import android.util.Log;

import com.hq.hlibrary.net.rx.upload.UploadInterceptor;
import com.hq.hlibrary.net.rx.upload.UploadProgressListener;
import com.trello.rxlifecycle3.android.ActivityEvent;

import java.lang.ref.SoftReference;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author ruowuming
 * @version 1.0
 * @date 2018/3/29  16:47
 */

public class HttpManager {
    private static final int DEFAULT_TIMEOUT = 30;
    private volatile static HttpManager instance;


    public static HttpManager getInstance(){
        if (instance==null){
            synchronized (HttpManager.class){
                instance=new HttpManager();
            }
        }
        return instance;
    }

    private HttpManager(){

    }

    public void doNet(BaseNetApi baseNetApi){
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        httpClient.writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        httpClient.readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        httpClient.sslSocketFactory(HttpsUtils.createSSLSocketFactory(),new HttpsUtils.TrustAllManager());
//        httpClient.sslSocketFactory(HttpsUtils.overlockCard().getSocketFactory());
//        httpClient.hostnameVerifier(new HttpsUtils.TrustAllHostnameVerifier());


        if (RX.getDebugModel()){
                httpClient.addInterceptor(getHttpLoggingInterceptor());
        }

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseNetApi.getBaseUrl())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(httpClient.build())
                .build();

        DefaultObserver observer=new DefaultObserver(baseNetApi);
        Observable observable=baseNetApi.getObservable(retrofit)
                .retryWhen(new RetryWhenNetworkException(baseNetApi.getRetryCount(),baseNetApi.getRetryDelay(),baseNetApi.getRetryIncreaseDelay()))
                .compose(baseNetApi.getRxAppCompatActivity().bindUntilEvent(ActivityEvent.PAUSE))
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(baseNetApi);

        /*链接式对象返回*/
        SoftReference<HttpNextListener> httpOnNextListener = baseNetApi.getHttpNextListener();
        if (httpOnNextListener != null && httpOnNextListener.get() != null) {
            httpOnNextListener.get().onNext(baseNetApi.getRequestWhat(),observable);
        }

        /*数据回调*/
        observable.subscribe(observer);
    }

    public void upLoad(BaseNetApi baseNetApi, UploadProgressListener listener){
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        httpClient.writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        httpClient.readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        httpClient.sslSocketFactory(HttpsUtils.createSSLSocketFactory(),new HttpsUtils.TrustAllManager());
        httpClient.addInterceptor(new UploadInterceptor(listener));
//        httpClient.sslSocketFactory(HttpsUtils.overlockCard().getSocketFactory());
//        httpClient.hostnameVerifier(new HttpsUtils.TrustAllHostnameVerifier());



        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseNetApi.getBaseUrl())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(httpClient.build())
                .build();

        DefaultObserver observer=new DefaultObserver(baseNetApi);
        Observable observable=baseNetApi.getObservable(retrofit)
                .retryWhen(new RetryWhenNetworkException(baseNetApi.getRetryCount(),baseNetApi.getRetryDelay(),baseNetApi.getRetryIncreaseDelay()))
                .compose(baseNetApi.getRxAppCompatActivity().bindUntilEvent(ActivityEvent.PAUSE))
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(baseNetApi);

        /*链接式对象返回*/
        SoftReference<HttpNextListener> httpOnNextListener = baseNetApi.getHttpNextListener();
        if (httpOnNextListener != null && httpOnNextListener.get() != null) {
            httpOnNextListener.get().onNext(baseNetApi.getRequestWhat(),observable);
        }

        /*数据回调*/
        observable.subscribe(observer);
    }


    /**
     * 日志输出
     * 自行判定是否添加
     * @return
     */
    private HttpInterceptor getHttpLoggingInterceptor(){
        //日志显示级别
        HttpInterceptor.Level level= HttpInterceptor.Level.BODY;
        //新建log拦截器
        HttpInterceptor loggingInterceptor=new HttpInterceptor(new HttpInterceptor.Logger() {
            @Override
            public void log(String message) {
                Log.d("RxRetrofit","Retrofit====Message:"+message);
            }
        });
        loggingInterceptor.setLevel(level);
        return loggingInterceptor;
    }


}
