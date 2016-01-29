package com.lncosie.zigbee.model

import com.lncosie.kab.Column
import com.lncosie.kab.TableName
import com.lncosie.kab.sql.Table

@TableName("auth")
class UserAuth : Table() {
    @Column()
    public @JvmField var auth_id: Int = 0
    @Column()
    public @JvmField var passwrod: String? = null
    @Column()
    public @JvmField var user_id: Int = 0
}
