package com.hq.hlibrary.net.rx.down;

/**
 * @author ruowuming
 * @version 1.0
 * @date 2018/4/3  10:48
 */
public interface DownProgressListener {
     void onProgress(long progress, long total, boolean done);
}
