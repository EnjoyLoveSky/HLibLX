package com.hq.hlibrary.net

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
class ApiException(message: String) : IOException(message)