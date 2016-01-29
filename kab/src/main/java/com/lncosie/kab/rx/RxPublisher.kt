package com.lncosie.kab.rx

open class RxPublisher<T>() : Spread<T, T>(null as ISubscribe<T>), IPublish<T> {
    override fun next(iter: T) {
        publisher.next(iter)
    }

    override fun error(e: Any) {
        publisher.error(e)
    }

    override fun complete() {
        publisher.complete()
    }

    override fun publish() {

    }
}

/***
 *
 *
 *
fun craft() {
var sub = Publisher<Int>();
val print = object : IPublish<Any> {
override fun error(e: Any) {
print(e)
}

override fun next(iter: Any) {
println(iter)
}

override fun complete() {
println("end")
}
}
val s = sub.take(4).drop(0).fold(0) { a, b -> a + b }.map { it }.subcrib(print)
//sub.publish()
sub.next(1);

println("ffff")
sub.subcrib(print)
sub.publish()
sub.next(1);
sub.next(1);
sub.next(1);
sub.next(1);
sub.next(1);
sub.next(1);

}
 * */
