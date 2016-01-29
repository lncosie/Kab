package com.lncosie.kab.sql
import com.lncosie.kab.Kab

public open class Table : View() {
    public var fireEvent:Boolean=false
    public open fun save() {
        Kab.sql.save(this)
    }

    public fun delete() {
        Kab.sql.delete(this)
    }
}