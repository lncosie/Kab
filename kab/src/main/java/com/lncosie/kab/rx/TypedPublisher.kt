package com.lncosie.kab.rx

import android.os.Handler
import android.os.Looper
import com.lncosie.kab.ThreadMode
import com.lncosie.kab.thread.ThreadExecutor
import java.lang.reflect.Method
import java.util.*
import java.util.concurrent.SynchronousQueue
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit

class TypedPublisher(type:Class<*>): ArrayList<TypedPublisher.CallSite>(), IPublish<Any> {
    data class CallSite(val site:Any, val method: Method, val mode: ThreadMode)

    override fun next(message: Any) {
        forEach {
            ThreadExecutor.runThreadMode(
                    {it.method.invoke(it.site,message)},
                    it.mode)
        }
    }

    override fun error(e: Any) {
        throw UnsupportedOperationException()
    }

    override fun complete() {
        throw UnsupportedOperationException()
    }
}
