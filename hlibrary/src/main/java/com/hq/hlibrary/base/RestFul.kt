package com.hq.hlibrary.base

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
 * @date  2019/6/24
 */

data class RestFul<out T>(
		val error: Int,// 0
		val message: String,// 成功
		val data: T
)
