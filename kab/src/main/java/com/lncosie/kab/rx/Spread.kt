package com.lncosie.kab.rx

import java.util.*

open abstract class Spread<T, N>(var sub: ISubscribe<T>) : ISubscribe<N> {

    protected var joints = LinkedList<Joint<N>>();

    class Joint<N>(val joints: MutableList<Joint<N>>, val sub: ISubscribe<N>, val pub: IPublish<N>) : IJoint<N> {
        override fun connect() {
            joints.add(this)
        }

        override fun disconnect() {
            joints.remove(this)
        }

        override val subscribe: ISubscribe<N>
            get() = sub

    }

    protected val publisher = object : IPublish<T> {
        override fun next(iter: T) {
            loop(iter)
        }

        override fun error(e: Any) {
            joints.forEach { it.pub.error(e) }
        }

        override fun complete() {
            joints.forEach { it.pub.complete() }
        }
    }

    init {
        sub.subcrib(publisher)
    }

    override fun subcrib(pub: IPublish<N>): IJoint<N> {
        val joint = Joint<N>(joints, this, pub)
        joint.connect()
        return joint
    }

    override fun publish() {
        sub.publish()
    }

    open fun loop(iter: T) {
        joints.forEach { it.pub.next(iter as N) }
    }
}