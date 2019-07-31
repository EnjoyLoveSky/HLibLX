package com.hq.hlibrary.net.rx;

import io.reactivex.Observable;
import retrofit2.Retrofit;

/**
 * @author ruowuming
 * @version 1.0
 * @date 2018/4/2  9:28
 */
public interface RequestListener {
    Observable onRequest(int what, Retrofit retrofit);

}
