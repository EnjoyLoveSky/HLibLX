package com.hq.hlibrary.base

import io.reactivex.Observable

/**
 *
 * You may think you know what the following code does.
 * But you dont. Trust me.
 * Fiddle with it, and youll spend many a sleepless
 * night cursing the moment you thought youd be clever
 * enough to "optimize" the code below.
 * Now close this file and go play with something else.
 * @Project lixing_android
 * @author ruowuming
 * @date  2019/6/24  16:17
 * @version 1.0
 */
open class BaseRepository {
    suspend fun <T : Any> doNet(call: suspend () -> Observable<RestFul<T>>): Observable<RestFul<T>> {
        return call.invoke()
    }

    suspend fun <T : Any> doNetX(call: suspend () -> RestFul<T>): RestFul<T> {
        return call.invoke()
    }

}