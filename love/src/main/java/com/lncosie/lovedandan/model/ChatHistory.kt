package com.lncosie.lovedandan.model

import com.lncosie.kab.Column
import com.lncosie.kab.TableName
import com.lncosie.kab.ViewName
import com.lncosie.kab.sql.Table
import com.lncosie.kab.sql.View

@ViewName("chats_history","select * from chats left join users on chats.user=users.user")
class ChatHistory : View(){
    @Column
    public @JvmField var user:String = ""
    @Column
    public @JvmField var message:String = ""
}