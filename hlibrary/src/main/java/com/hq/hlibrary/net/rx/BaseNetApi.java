package com.hq.hlibrary.net.rx;

import android.text.TextUtils;

import com.trello.rxlifecycle3.components.support.RxAppCompatActivity;


import java.lang.ref.SoftReference;

import io.reactivex.Observable;
import io.reactivex.functions.Function;
import retrofit2.Retrofit;


/**
 * @author ruowuming
 * @version 1.0
 * @date 2018/3/29  16:57
 */
public abstract class BaseNetApi<T> implements Function<RestFulX<T>, T> {

    /**
     * rx的生命周期管理
     */
    private SoftReference<RxAppCompatActivity> rxSoftReference;
    /**
     * 回调
     */
    private SoftReference<HttpNextListener> httpNextListener;
    /**
     * 是否可以取消
     */
    private boolean canCancel;
    /**
     * 是否显示Dialog
     */
    private boolean showDialog;
    /**
     * 是否缓存
     */
    private boolean canCache;
    /**
     * BaseUrl
     */
    private String baseUrl = "";
    /********************************************************
     *                                                      *
     *           暂不实现本地网络缓存                        *
     *                                                      *
     ********************************************************/
    /**
     * 缓存路径
     */
    private String cachePath = "";
    /**
     * 缓存url-可手动设置
     */
    private String cacheUrl;
    /**
     * 超时时间
     */
    private long CONNECT_TIME_OUT = 6 * 1000L;
    /**
     * 有网cookie缓存时间
     */
    private long cookieNetCacheTime = 60 * 1000L;
    /**
     * 无网络cookie缓存时间
     */
    private long cookieUnNetCacheTime = 30 * 24 * 60 * 60 * 1000L;
    /**
     * 重试次数
     */
    private int rentyWhen = 3;
    /**
     * 失败后retry次数
     */
    private int retryCount = 1;
    /**
     * 失败后retry延迟
     */
    private long retryDelay = 100;
    /**
     * 失败后retry叠加延迟
     */
    private long retryIncreaseDelay = 10;
    /**
     * 请求标识
     */
    private int requestWhat = 9999;

    public BaseNetApi(HttpNextListener httpOnNext, RxAppCompatActivity rxAppCompatActivity) {
        setHttpNextListener(httpOnNext);
        setRxAppCompatActivity(rxAppCompatActivity);
        setShowDialog(true);
        setCanCancel(true);
    }


    @Override
    public T apply(RestFulX<T> tRestFul) {
        System.out.println("tRestFul = [" + tRestFul.toString() + "]"+",what="+getRequestWhat());
        if (tRestFul.getError() != 0) {
            throw new TimeException(tRestFul.getMessage());
        }
//        if (getRequestWhat() == Constant.RequestWhat.REQUEST_NEWS_DETAILS ||
//                getRequestWhat() == Constant.RequestWhat.REQUEST_COMMIT ||
//                getRequestWhat() == Constant.RequestWhat.REQUEST_UPLOAD_LIST ||
//                getRequestWhat() == Constant.RequestWhat.REQUEST_REPORT_LIST ||
//                getRequestWhat() == Constant.RequestWhat.REQUEST_SEND_CODE ||
//                getRequestWhat() == Constant.RequestWhat.REQUEST_FORGET_PWD||
//                getRequestWhat() == Constant.RequestWhat.REQUEST_COLLECT_SELECT||
//                getRequestWhat() == Constant.RequestWhat.REQUEST_COLLECT_CANCEL||
//                getRequestWhat() == Constant.RequestWhat.REQUEST_DOC_BIND ||
//                getRequestWhat() == Constant.RequestWhat.REQUEST_RESTFUL_X||
//                getRequestWhat() == Constant.RequestWhat.REQUEST_RESTFUL_Y ||
//                getRequestWhat() == Constant.RequestWhat.REQUEST_BIND
//        ){
//            return (T) tRestFul;
//        }
        return tRestFul.getData();
    }

    /**
     * 获取Observable,并链式设置参数
     */
    public abstract Observable getObservable(Retrofit retrofit);


    public int getRequestWhat() {
        return requestWhat;
    }

    public void setRequestWhat(int requestWhat) {
        this.requestWhat = requestWhat;
    }


    public RxAppCompatActivity getRxAppCompatActivity() {
        return rxSoftReference.get();
    }

    public void setRxAppCompatActivity(RxAppCompatActivity rxAppCompatActivity) {
        this.rxSoftReference = new SoftReference(rxAppCompatActivity);
    }

    public SoftReference<HttpNextListener> getHttpNextListener() {
        return httpNextListener;
    }

    public void setHttpNextListener(HttpNextListener httpNextListener) {
        this.httpNextListener = new SoftReference(httpNextListener);
    }

    public boolean isCanCancel() {
        return canCancel;
    }

    public void setCanCancel(boolean canCancel) {
        this.canCancel = canCancel;
    }

    public boolean isShowDialog() {
        return showDialog;
    }


    public boolean isCanCache() {
        return canCache;
    }

    public void setCanCache(boolean canCache) {
        this.canCache = canCache;
    }

    public void setShowDialog(boolean showDialog) {
        this.showDialog = showDialog;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    /**
     * 默认缓存路径
     *
     * @return str
     */
    public String getDefaultCacheUrl() {
        if (TextUtils.isEmpty(getCacheUrl())) {
            return getBaseUrl() + getCachePath();
        }
        return getCacheUrl();
    }

    public String getCachePath() {
        return cachePath;
    }

    public void setCachePath(String cachePath) {
        this.cachePath = cachePath;
    }

    public String getCacheUrl() {
        return cacheUrl;
    }

    public void setCacheUrl(String cacheUrl) {
        this.cacheUrl = cacheUrl;
    }

    public long getConnectTimeOut() {
        return CONNECT_TIME_OUT;
    }

    public void setConnectTimeOut(long CONNECT_TIME_OUT) {
        this.CONNECT_TIME_OUT = CONNECT_TIME_OUT;
    }

    public long getCookieNetCacheTime() {
        return cookieNetCacheTime;
    }

    public void setCookieNetCacheTime(long cookieNetCacheTime) {
        this.cookieNetCacheTime = cookieNetCacheTime;
    }

    public long getCookieUnNetCacheTime() {
        return cookieUnNetCacheTime;
    }

    public void setCookieUnNetCacheTime(long cookieUnNetCacheTime) {
        this.cookieUnNetCacheTime = cookieUnNetCacheTime;
    }

    public int getRentyWhen() {
        return rentyWhen;
    }

    public void setRentyWhen(int rentyWhen) {
        this.rentyWhen = rentyWhen;
    }

    public int getRetryCount() {
        return retryCount;
    }

    public void setRetryCount(int retryCount) {
        this.retryCount = retryCount;
    }

    public long getRetryDelay() {
        return retryDelay;
    }

    public void setRetryDelay(long retryDelay) {
        this.retryDelay = retryDelay;
    }

    public long getRetryIncreaseDelay() {
        return retryIncreaseDelay;
    }

    public void setRetryIncreaseDelay(long retryIncreaseDelay) {
        this.retryIncreaseDelay = retryIncreaseDelay;
    }
}
