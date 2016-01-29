package com.lncosie.kab.event

import com.lncosie.kab.sql.Table

data class TableChanged(val change: TableChanged.Change, val data: Table){
    public enum class Change{
        ADD,UPDATE,DELETE
    }
}