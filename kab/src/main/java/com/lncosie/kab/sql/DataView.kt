package com.lncosie.kab.sql

import com.lncosie.kab.Kab
import java.util.*

public open class DataView<T>(val coClass: Class<T>, var sql: String) : ArrayList<T>() {
    protected fun selectAll() {
        Kab.sql.select(coClass, sql).forEach {
            add(it as T)
        }
    }

    public fun update() {
        clear()
        selectAll()
    }
}