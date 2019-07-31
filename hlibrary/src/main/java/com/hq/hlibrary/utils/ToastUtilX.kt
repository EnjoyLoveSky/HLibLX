package com.hq.hlibrary.utils

import android.content.Context
import android.widget.Toast
import com.hq.hlibrary.net.RxHelper
import io.reactivex.Observable

/**
 *
 *
 * toast工具类封装
 */
object ToastUtilX {
    private var mToast: Toast? = null

    /**
     * 显示一个toast提示
     *
     * @param resourceId toast字符串资源id
     */
    fun showToast(resourceId: Int) {
        showToast(ResourcesUtils.getString(resourceId))
    }


    fun showToast(text: String) {
        showToast(AppInfoUtils.context, text, Toast.LENGTH_SHORT)
    }

    /**
     * 显示一个toast提示
     *
     * @param text     toast字符串
     * @param duration toast显示时间
     */
    fun showToast(text: String, duration: Int = Toast.LENGTH_SHORT) {
        showToast(AppInfoUtils.context, text, duration)
    }

    /**
     * 显示一个toast提示
     *
     * @param context  context 上下文对象
     * @param text     toast字符串
     * @param duration toast显示时间
     */
    fun showToast(context: Context, text: String, duration: Int) {
        /**
         * 保证运行在主线程
         */
        val disposable = Observable.just(0)
                .compose(RxHelper.rxSchedulerHelper())
                .subscribe {
                    if (mToast == null) {
                        mToast = Toast.makeText(context, text, duration)
                    } else {
                        mToast!!.setText(text)
                        mToast!!.duration = duration
                    }
                    mToast!!.show()
                }
    }
}
/**
 * 显示一个toast提示
 *
 * @param text toast字符串
 */
