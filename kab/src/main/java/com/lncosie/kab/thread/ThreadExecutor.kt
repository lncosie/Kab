package com.lncosie.kab.thread

import android.os.Handler
import android.os.Looper
import com.lncosie.kab.Kab
import com.lncosie.kab.ThreadMode
import java.util.concurrent.SynchronousQueue
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit

class ThreadExecutor {
    companion object {
        lateinit var uiHandle:Handler
        lateinit var executor :ThreadPoolExecutor

        fun threadPoolInit(corePoolSize:Int=2){
            uiHandle = Handler(Looper.getMainLooper())
            executor= ThreadPoolExecutor(corePoolSize, Integer.MAX_VALUE, 1, TimeUnit.MINUTES, SynchronousQueue<Runnable>(), ThreadPoolExecutor.CallerRunsPolicy())
        }

        inline fun runThreadMode(crossinline fn: () -> Unit, mode: ThreadMode) {
            when (mode) {
                ThreadMode.UI -> runUiThread(fn)
                ThreadMode.WORKER -> runWorkThread(fn);
                ThreadMode.ANY -> fn()
            }
        }

        inline fun runUiThread(crossinline fn: () -> Unit) {
            if (Looper.myLooper() == Looper.getMainLooper()) {
                fn()
            } else {
                uiHandle.post { fn() }
            }
        }

        inline fun runWorkThread(crossinline fn: () -> Unit) {
            executor.submit { fn() }
        }
    }
}
