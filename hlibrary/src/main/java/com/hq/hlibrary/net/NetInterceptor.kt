package com.hq.hlibrary.net

import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

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
class NetInterceptor(private val handler: RequestHandler?) : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()


        if (handler != null) {
            request = handler.onBeforeRequest(request, chain)
        }

        val response = chain.proceed(request)


        if (handler != null) {
            val tmp = handler.onAfterRequest(response, chain)
            if (tmp != null) {
                return tmp
            }

        }
        return response
    }
}