package com.lncosie.kab.rx

interface ISubscribe<T> : ISource<T> {
    fun subcrib(subscriber: IPublish<T>): IJoint<T>
}