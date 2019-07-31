package com.hq.hlibrary.net

import android.content.Context
import com.franmontiel.persistentcookiejar.PersistentCookieJar
import com.franmontiel.persistentcookiejar.cache.SetCookieCache
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor
import com.hq.hlibrary.BuildConfig
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import java.io.IOException
import java.util.*

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
class BaseNetProvider(private val mContext: Context) : NetProvider {
//    private val interceptor = run {
//        val httpLoggingInterceptor = HttpLoggingInterceptor()
//        httpLoggingInterceptor.apply { httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY }
//    }

    var interceptor =HttpLoggingInterceptor()

    override fun configInterceptors(): Array<Interceptor>? {
        interceptor.level=HttpLoggingInterceptor.Level.BODY
        return arrayOf(interceptor)
    }

    override fun configHttps(builder: OkHttpClient.Builder) {

    }

    override fun configCookie(): CookieJar? =
            PersistentCookieJar(SetCookieCache(), SharedPrefsCookiePersistor(mContext))

    override fun configHandler(): RequestHandler {

        return HeaderHandler()
    }

    override fun configConnectTimeoutSecs(): Long {
        return CONNECT_TIME_OUT
    }

    override fun configReadTimeoutSecs(): Long {
        return READ_TIME_OUT
    }

    override fun configWriteTimeoutSecs(): Long {
        return WRITE_TIME_OUT
    }

    override fun configLogEnable(): Boolean {
        return BuildConfig.DEBUG
    }

    private val traceId: String
        get() = UUID.randomUUID().toString()

    inner class HeaderHandler : RequestHandler {

        override fun onBeforeRequest(request: Request, chain: Interceptor.Chain): Request {
            return request
        }

        @Throws(IOException::class)
        override fun onAfterRequest(response: Response, chain: Interceptor.Chain): Response {
            var e: ApiException? = null
            when {
                401 == response.code -> throw ApiException("请求错误!")
                403 == response.code -> e = ApiException("禁止访问!")
                404 == response.code -> e = ApiException("链接错误")
                503 == response.code -> e = ApiException("服务器升级中!")
                response.code > 300 -> {
                    val message = response.body!!.string()
                    e = if (Utils.check(message)) {
                        ApiException("服务器内部错误!")
                    } else {
                        ApiException(message)
                    }
                }
            }
            if (!Utils.check(e)) {
                throw e!!
            }
            return response
        }
    }

    companion object {

        const val CONNECT_TIME_OUT: Long = 20
        const val READ_TIME_OUT: Long = 180
        const val WRITE_TIME_OUT: Long = 30
    }

    internal object Utils {
        fun check(message: String?): Boolean = message.isNullOrEmpty()

        fun check(o: Any?): Boolean = o ==null
    }

}
