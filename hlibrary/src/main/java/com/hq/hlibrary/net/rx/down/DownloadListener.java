package com.hq.hlibrary.net.rx.down;

/**
 * @author ruowuming
 * @version 1.0
 * @date 2018/4/3  10:48
 */

public interface DownloadListener<T> {

    void onDownStart();

    void onDownProgress(int p);

    void onDownSuccess(T responseBody);

    void onDownError(String e);

    void onDownComplete();

}
