package com.hq.hlibrary.net.rx;

import android.content.Context;

import com.hq.hlibrary.utils.ToastUtilX;
import com.trello.rxlifecycle3.components.support.RxAppCompatActivity;

import java.lang.ref.SoftReference;
import java.net.ConnectException;
import java.net.SocketTimeoutException;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * @author ruowuming
 * @version 1.0
 * @date 2018/3/29  11:52
 */
public  class DefaultObserver<T> extends io.reactivex.observers.DefaultObserver<T> {

    /*是否弹框*/
    private boolean isShowDialog = true;
    /* 软引用回调接口*/
    private SoftReference<HttpNextListener> httpNextListener;
    /*软引用反正内存泄露*/
    private SoftReference<RxAppCompatActivity> rxAppCompatActivity;
    /*加载框可自己定义*/
    private LoadViewUtils dialogUtils;
    /*请求数据*/
    private BaseNetApi baseNetApi;
    private Disposable disposable;

    public DefaultObserver(BaseNetApi api) {
        this.baseNetApi = api;
        this.httpNextListener = api.getHttpNextListener();
        this.rxAppCompatActivity = new SoftReference(api.getRxAppCompatActivity());
        setShowDialog(api.isShowDialog());
    }

    @Override
    protected void onStart() {
        super.onStart();
//        dialogUtils = new LoadViewUtils();
        if (baseNetApi.isShowDialog()) {
            dialogUtils.showProgress(baseNetApi.getRxAppCompatActivity(), baseNetApi.isCanCancel());
            dialogUtils.setCancelListener((HttpNextListener) baseNetApi.getHttpNextListener().get(),disposable,baseNetApi.getRequestWhat());
        }
    }

    @Override
    public void onNext(T t) {
        if (httpNextListener != null) {
            httpNextListener.get().onNext(baseNetApi.getRequestWhat(),t);
        }
    }


    @Override
    public void onComplete() {
        if (dialogUtils!=null)
        dialogUtils.dismissProgress();
    }

    @Override
    public void onError(Throwable e) {
        if (dialogUtils!=null)
        dialogUtils.dismissProgress();
        if (baseNetApi.isCanCache()){
            Observable.just(baseNetApi.getDefaultCacheUrl())
                    .subscribe(new Observer<String>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(String s) {
                              /**获取本地数据库缓存数据*/
                        }

                        @Override
                        public void onError(Throwable e) {
                            error(e);
                        }

                        @Override
                        public void onComplete() {

                        }
                    });
        }else {
            error(e);
        }
    }

    /**错误统一处理*/
    private void error(Throwable e) {
        Context context = rxAppCompatActivity.get();
        if (context == null) return;
        if (e instanceof SocketTimeoutException) {
            ToastUtilX.INSTANCE.showToast("网络中断，请检查您的网络状态");
        } else if (e instanceof ConnectException) {
            ToastUtilX.INSTANCE.showToast("网络中断，请检查您的网络状态");
        } else {
            ToastUtilX.INSTANCE.showToast("错误:" + e.getMessage());
        }
        if (httpNextListener.get() != null) {
            httpNextListener.get().onError(baseNetApi.getRequestWhat(),e);
        }
    }

    public boolean isShowDialog() {
        return isShowDialog;
    }

    public void setShowDialog(boolean showDialog) {
        isShowDialog = showDialog;
    }


}
