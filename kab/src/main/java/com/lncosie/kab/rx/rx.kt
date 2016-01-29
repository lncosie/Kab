package com.lncosie.kab.rx


open class Transform<T, N>(sub: ISubscribe<T>, val fn: (T) -> N) : Spread<T, N>(sub) {
    override fun loop(iter: T) {
        val next = fn(iter)
        joints.forEach { it.pub.next(next) }
    }
}

open class FoldLeft<T, N>(sub: ISubscribe<T>, val fn: (N, T) -> N, init: N) : Spread<T, N>(sub) {
    var start: N = init;
    override fun loop(iter: T) {
        start = fn(start, iter)
        joints.forEach { it.pub.next(start) }
    }
}

open class Predict<T>(sub: ISubscribe<T>, val predict: (T) -> Boolean) : Spread<T, T>(sub) {
    override fun loop(iter: T) {
        val pass = predict(iter)
        if (pass)
            joints.forEach { it.pub.next(iter) }
    }
}

open class Cut<T>(sub: ISubscribe<T>, val predict: (T) -> Boolean) : Spread<T, T>(sub) {
    override fun loop(iter: T) {
        val pass = predict(iter)
        if (pass)
            joints.forEach { it.pub.next(iter) }
        else
            joints.forEach {
                it.pub.complete()
                it.disconnect()
            }
    }
}

fun<T> asSubscribe(vararg iter: T): ISubscribe<T> {
    var pub: IPublish<T>? = null;
    var sub: ISubscribe<T>? = null;
    class Joint(val publish: IPublish<T>) : IJoint<T> {
        override fun connect() {
            pub = publish
        }
        override fun disconnect() {
            pub = null
        }
        override val subscribe: ISubscribe<T>
            get() = sub!!
    }
    sub = object : ISubscribe<T> {
        override fun subcrib(publish: IPublish<T>): IJoint<T> {
            pub = publish
            return Joint(publish)
        }

        override fun publish() {
            iter.forEach { pub?.next(it) }
            pub?.complete()
        }
    }
    val spread = Transform<T, T>(sub!!, { it })
    return spread;
}

fun<T, N> ISubscribe<T>.map(map: (T) -> N): ISubscribe<N> {
    val spread = Transform(this, map)
    return spread
}

fun<T, I> ISubscribe<T>.fold(init: I, fold: (I, T) -> I): ISubscribe<I> {
    val spread = FoldLeft(this, fold, init)
    return spread
}

fun<T> ISubscribe<T>.predict(condition: (T) -> Boolean): ISubscribe<T> {
    val spread = Predict(this, condition)
    return spread
}

fun<T> ISubscribe<T>.take(cnt: Int): ISubscribe<T> {
    var counter = 0;
    val take: (iter: T) -> Boolean = {
        counter++
        counter <= cnt
    }
    val spread = Predict(this, take)
    return spread
}

fun<T> ISubscribe<T>.drop(cnt: Int): ISubscribe<T> {
    var counter = 0;
    val take: (iter: T) -> Boolean = {
        counter++
        counter > cnt
    }
    val spread = Predict(this, take)
    return spread
}
