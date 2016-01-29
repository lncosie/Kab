package com.lncosie.lovedandan.model

import com.lncosie.kab.Column
import com.lncosie.kab.TableName
import com.lncosie.kab.sql.Table
@TableName("chats")
class Chat : Table(){
    @Column
    public @JvmField var user:String = ""
    @Column
    public @JvmField var message:String = ""

}