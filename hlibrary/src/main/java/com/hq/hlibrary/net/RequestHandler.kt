package com.hq.hlibrary.net

import okhttp3.Interceptor
import okhttp3.Request
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
interface RequestHandler {

    fun onBeforeRequest(request: Request, chain: Interceptor.Chain): Request

    @Throws(IOException::class)
    fun onAfterRequest(response: Response, chain: Interceptor.Chain): Response

}
