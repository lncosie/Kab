package com.lncosie.zigbee.model

import com.lncosie.kab.Column
import com.lncosie.kab.TableName
import com.lncosie.kab.sql.Table

@TableName("history")
class History : Table() {
    @Column()
    public @JvmField var auth_id: Int = 0
    @Column()
    public @JvmField var time: Long = 0

}