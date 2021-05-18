package com.yunshan.screenlock

import android.annotation.SuppressLint
import android.content.Context
import android.os.PowerManager

import android.os.PowerManager.WakeLock


/**
 * @author caizhaowei
 * @date 2021/5/18
 */
object PowerManagerWakeLock {

    private var wakeLock: WakeLock? = null

    /**开启 保持屏幕唤醒 */
    @SuppressLint("InvalidWakeLockTag")
    fun acquire(context: Context) {
        val pm = context.getSystemService(Context.POWER_SERVICE) as PowerManager
        wakeLock = pm.newWakeLock(
            PowerManager.SCREEN_BRIGHT_WAKE_LOCK or PowerManager.ON_AFTER_RELEASE,
            "PowerManagerWakeLock"
        )
        wakeLock?.acquire()

        println("ScreenGuard ==>>  开启保持屏幕唤醒")
    }

    /**关闭 保持屏幕唤醒 */
    fun release() {
        if (wakeLock != null) {
            wakeLock!!.release()
            wakeLock = null

            println("ScreenGuard ==>>  关闭保持屏幕唤醒")
        }
    }
}