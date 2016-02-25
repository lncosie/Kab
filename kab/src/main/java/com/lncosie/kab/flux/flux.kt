package com.lncosie.kab.flux

import com.lncosie.kab.Kab
import com.lncosie.kab.ThreadMode
import com.lncosie.kab.thread.ThreadExecutor
import java.lang.ref.WeakReference
import java.util.*
import java.util.concurrent.Executor

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
        stores.forEach {
            ThreadExecutor.runThreadMode(
                    {it.onAction(this,action)},
                    action.threadMode)
        }
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

class Action(val threadMode: ThreadMode) {
}

abstract class Store(val zoneName: String) {
    fun join(){
        Zone.getZone(zoneName)?.register(this)
    }
    fun leave(){
        Zone.getZone(zoneName)?.unRegister(this)
    }
    abstract fun onAction(zone: Zone, action: Action)
}



fun craft() {
    val ui = Zone("ui")
    val net = Zone("net")


    val store = object : Store("ui") {
        override fun onAction(zone: Zone, action: Action) {
            Zone.getZone("net")?.post(Action(ThreadMode.WORKER))
        }
    }
    val ns = object : Store("net") {
        override fun onAction(zone: Zone, action: Action) {

        }
    }

    store.join()
    ns.join()

    val action = Action(ThreadMode.UI)
    ui.post(action)

    ns.leave()
    store.leave()

    ui.release()
    net.release()
}
