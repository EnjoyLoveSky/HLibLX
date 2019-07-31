package com.hq.hlibrary.net

import okhttp3.CookieJar
import okhttp3.Interceptor
import okhttp3.OkHttpClient

/**
 *
 * You may think you know what the following code does.
 * But you dont. Trust me.
 * Fiddle with it, and youll spend many a sleepless
 * night cursing the moment you thought youd be clever
 * enough to "optimize" the code below.
 * Now close this file and go play with something else.
 * @author ruowuming
 * @date  2019/7
 * @version 1.0
 */
interface NetProvider {

    fun configInterceptors(): Array<Interceptor>?

    fun configHttps(builder: OkHttpClient.Builder)

    fun configCookie(): CookieJar?

    fun configHandler(): RequestHandler

    fun configConnectTimeoutSecs(): Long

    fun configReadTimeoutSecs(): Long

    fun configWriteTimeoutSecs(): Long

    fun configLogEnable(): Boolean

}