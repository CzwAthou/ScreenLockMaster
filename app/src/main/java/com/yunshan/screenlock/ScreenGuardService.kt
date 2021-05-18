package com.yunshan.screenlock

import android.app.KeyguardManager
import android.app.Service
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.IBinder
import android.view.WindowManager


/**
 * @author caizhaowei
 * @date 2021/5/18
 */
class ScreenGuardService : Service() {

    var screenReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            val action = intent.action
            if (action == Intent.ACTION_SCREEN_ON) {
                println("ScreenGuard ==>>  收到广播  ACTION_SCREEN_ON")
            } else if (action == Intent.ACTION_SCREEN_OFF) { //如果接受到关闭屏幕的广播
                println("ScreenGuard ==>>  收到广播  ACTION_SCREEN_OFF")

                if (!ScreenShowActivity.isShow) {
                    //开启屏幕唤醒，常亮
                    PowerManagerWakeLock.acquire(context)
                }

                PowerManagerWakeLock.acquire(context)

                val intent2 = Intent(context, ScreenShowActivity::class.java)
                intent2.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(intent2)

//                PowerManagerWakeLock.release()
            }
        }
    }

    override fun onCreate() {
        super.onCreate()
        val keyguardManager = getSystemService(Context.KEYGUARD_SERVICE) as KeyguardManager
        val keyguardLock = keyguardManager.newKeyguardLock("KeyguardLock")
        keyguardLock.disableKeyguard();

        // 注册一个监听屏幕开启和关闭的广播
        val filter = IntentFilter()
        filter.addAction(Intent.ACTION_SCREEN_ON)
        filter.addAction(Intent.ACTION_SCREEN_OFF)
        registerReceiver(screenReceiver, filter)
    }

    override fun onDestroy() {
        super.onDestroy()
        PowerManagerWakeLock.release();
        unregisterReceiver(screenReceiver);
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }
}