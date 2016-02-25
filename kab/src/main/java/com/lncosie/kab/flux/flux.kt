package com.lncosie.kab.flux

import java.lang.ref.WeakReference
import java.util.*

/**
 * flux  framework component
 **/
class Zone(val name: String) {
    val stores = ArrayList<Store>()
    fun register(store: Store) {
        stores.add(store)
    }

    fun unRegister(store: Store) {
        stores.remove(store)
    }

    fun post(action: Action) {
        stores.forEach { it.onAction(this, action) }
    }

    init {
        zones.put(name.hashCode(), WeakReference<Zone>(this))
    }

    fun release() {
        zones.remove(name.hashCode())
    }

    override fun hashCode(): Int {
        return name.hashCode()
    }

    companion object {
        val zones = LinkedHashMap<Int, WeakReference<Zone>>()
        fun getZone(name: String): Zone? {
            return zones.get(name.hashCode())?.get()
        }
    }
}

class Action {
    var model: Model? = null
}

abstract class Store(val zoneName: String) {
    abstract fun onAction(zone: Zone, action: Action)
    fun post(action: Action) {
        Zone.getZone(zoneName)?.post(action)
    }
}

interface Model {

}

fun craft() {
    val ui = Zone("ui")
    val net = Zone("net")


    val store = object : Store("ui") {
        override fun onAction(zone: Zone, action: Action) {
            Zone.getZone("net")?.post(Action())
        }
    }
    val ns = object : Store("net") {
        override fun onAction(zone: Zone, action: Action) {

        }
    }

    ui.register(store)
    net.register(ns)

    val action = Action()
    ui.post(action)

    net.unRegister(ns)
    ui.register(store)

    ui.release()
    net.release()
}
