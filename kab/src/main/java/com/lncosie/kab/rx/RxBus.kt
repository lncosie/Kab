package com.lncosie.kab.rx

import java.util.*

public object  RxBus{
    public fun post(e:Any){
        dispatchers.get(e.javaClass)?.next(e)
    }
    val dispatchers=HashMap<Class<*>,TypedPublisher>()
}


