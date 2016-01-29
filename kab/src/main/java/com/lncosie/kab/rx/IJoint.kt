package com.lncosie.kab.rx

interface IJoint<T> {
    fun connect()
    fun disconnect()
    val subscribe: ISubscribe<T>
}