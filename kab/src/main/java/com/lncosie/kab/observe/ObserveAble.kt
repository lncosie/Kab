package com.lncosie.kab.observe

import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

class ObserveAble<T>(var value:T?=null, val fn:((T)->Unit)?=null) : ReadWriteProperty<Any, T> {
    public override fun getValue(thisRef: Any, property: KProperty<*>): T {
        return value!!
    }

    public override fun setValue(thisRef: Any, property: KProperty<*>, value: T) {
        this.value = value
        fn?.invoke(value)
    }
}