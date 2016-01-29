package com.lncosie.kab.rx

import android.os.Handler
import android.os.Looper
import com.lncosie.kab.ThreadMode
import java.lang.reflect.Method
import java.util.*
import java.util.concurrent.SynchronousQueue
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit

class TypedPublisher(type:Class<*>): ArrayList<TypedPublisher.CallSite>(), IPublish<Any> {
    data class CallSite(val site:Any, val method: Method, val mode: ThreadMode)
    val postor=Handler(Looper.getMainLooper())
    val executor=ThreadPoolExecutor(2, Integer.MAX_VALUE, 1, TimeUnit.MINUTES, SynchronousQueue<Runnable>(), ThreadPoolExecutor.CallerRunsPolicy());

    override fun next(message: Any) {
        forEach {
            when(it.mode){
                ThreadMode.UI ->runUiThread(it,message)
                ThreadMode.WORKER->runWorkThread(it,message)
                else->it.method.invoke(it.site,message)
            }
        }
    }
    inline fun runUiThread(site:CallSite,message:Any){
        if(Looper.myLooper() == Looper.getMainLooper()){
            site.method.invoke(site.site,message)
        }else{
            postor.post { site.method.invoke(site.site,message) }
        }
    }
    inline fun runWorkThread(site:CallSite,message:Any){
        executor.submit{ site.method.invoke(site.site,message) }
    }
    override fun error(e: Any) {
        throw UnsupportedOperationException()
    }

    override fun complete() {
        throw UnsupportedOperationException()
    }
}
