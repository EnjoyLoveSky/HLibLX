package com.hq.hlibrary.net.rx;

import io.reactivex.Observable;

/**
 * 网络请求成功回掉处理
 * @author ruowuming
 * @version 1.0
 * @date 2018/3/29  17:12
 */
public abstract class HttpNextListener<T> {

    /**
     * 成功后回调方法
     * @param t
     */
    public abstract void onNext(int what,T t);

    /**
     * 缓存回调结果
     * @param string
     */
    public void onCacheNext(int what, String string){

    }

    /**
     * 成功后的ober返回，扩展链接式调用
     * @param observable
     */
    public void onNext(int what, Observable observable){

    }

    /**
     * 失败或者错误方法
     * 主动调用，更加灵活
     * @param e
     */
    public  void onError(int what, Throwable e){

    }

    /**
     * 取消回調
     */
    public void onCancel(int what){

    }

}
