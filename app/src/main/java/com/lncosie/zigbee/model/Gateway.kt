package com.lncosie.zigbee.model

import com.lncosie.kab.Column
import com.lncosie.kab.TableName
import com.lncosie.kab.sql.Table

@TableName("gateway")
open class Gateway() : Table() {
    @Column()
    public @JvmField var name: String? = null
    @Column()
    public @JvmField var passwrod: String? = null
    @Column()
    public @JvmField var gateway_uid: String? = null

    fun devices(): Iterator<Device> {
        assert(id != null, { "gateway do not init" })
        return Companion.where<Device>("gateway_uid=?", id!!);
    }
}