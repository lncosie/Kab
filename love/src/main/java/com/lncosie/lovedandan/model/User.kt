package com.lncosie.lovedandan.model

import com.lncosie.kab.Column
import com.lncosie.kab.TableName
import com.lncosie.kab.sql.Table
@TableName("users")
class User : Table(){
    @Column
    public @JvmField var user:String = ""
}