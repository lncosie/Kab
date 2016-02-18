package com.lncosie.kab.flux
import java.util.*
/**
 * flux  framework component
 **/
class Zone(val name: String) {


    val stores=LinkedList<Store>()
    fun register(store:Store){
        stores.add(store)
    }
    fun unRegister(store:Store){
        stores.remove(store)
    }
    fun post(action: Action){
        stores.forEach { it.onAction(this,action) }
    }

    init{
        zones.add(this)
    }
    fun release(){
        zones.remove(this)
    }
    override fun hashCode(): Int {
        return name.hashCode()
    }
    companion object{
        val zones=HashSet<Zone>()
        fun getZone(name: String):Zone?=null
    }
}
class Action{
    var model:Model?=null
}
interface Store{
    fun onAction(zone: Zone, action: Action)
}
interface Model{

}

fun craft(){
    val ui= Zone("ui")
    val net= Zone("net")


    val store=object:Store{
        override fun onAction(zone: Zone, action: Action) {
            Zone.getZone("net")?.post(Action())
        }
    }
    val ns=object:Store{
        override fun onAction(zone: Zone, action: Action) {

        }
    }

    ui.register(store)
    net.register(ns)

    val action=Action()
    ui.post(action)

    net.unRegister(ns)
    ui.register(store)

    ui.release()
    net.release()
}
