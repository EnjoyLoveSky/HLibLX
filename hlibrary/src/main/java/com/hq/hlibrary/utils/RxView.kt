package com.hq.hlibrary.utils

import android.view.View
import com.jakewharton.rxbinding3.view.clicks
import io.reactivex.Observable
import java.util.concurrent.TimeUnit

fun View.clicksThrottleFirst(): Observable<Unit> {
    return clicks().throttleFirst(1000, TimeUnit.MILLISECONDS)
}

fun View.clicksThrottleFirst(mill:Long): Observable<Unit> {
    return clicks().throttleFirst(mill, TimeUnit.MILLISECONDS)
}