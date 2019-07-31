package com.hq.hlibrary.net.okhttp.cache;

import com.hq.hlibrary.utils.AppInfoUtils;
import okhttp3.Cache;

import java.io.File;

/**
 */
public class HttpCache {

    private static final int HTTP_RESPONSE_DISK_CACHE_MAX_SIZE = 50 * 1024 * 1024;

    public static Cache getCache() {
        return new Cache(new File(AppInfoUtils.INSTANCE.getContext().getExternalCacheDir().getAbsolutePath() + File
                .separator + "data/NetCache"),
                HTTP_RESPONSE_DISK_CACHE_MAX_SIZE);
    }
}
