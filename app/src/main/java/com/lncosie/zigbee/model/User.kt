package com.lncosie.zigbee.model

import com.lncosie.kab.Column
import com.lncosie.kab.TableName
import com.lncosie.kab.sql.Table
import com.lncosie.kab.sql.View

@TableName("user")
class User : Table() {
    @Column()
    public @JvmField var name: String? = null
    @Column()
    public @JvmField var notify: Boolean = false
    @Column()
    public @JvmField var device_id: Int = 0
    @Column()
    public @JvmField var image_def: Int = 0
    @Column()
    public @JvmField var image: ByteArray? = null

    fun auths(): Iterator<UserAuth> {
        assert(id != null, { "user do not init" })
        return View.where<UserAuth>("user_id=?", id!!);
    }
}