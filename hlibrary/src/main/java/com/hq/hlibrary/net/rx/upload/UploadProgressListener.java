package com.hq.hlibrary.net.rx.upload;

/**
 * @author ruowuming
 * @version 1.0
 * @date 2018/4/28  15:56
 */

public interface UploadProgressListener {
    /**
     * 上传进度
     * @param currentBytesCount
     * @param totalBytesCount
     */
    void onProgress(long currentBytesCount, long totalBytesCount);
}
