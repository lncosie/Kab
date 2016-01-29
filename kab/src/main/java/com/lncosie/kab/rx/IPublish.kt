package com.lncosie.kab.rx

interface IPublish<in T> {
    fun next(iter: T)
    fun error(e: Any)
    fun complete()
}