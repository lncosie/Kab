package com.lncosie.zigbee.model

import com.lncosie.kab.Column
import com.lncosie.kab.TableName
import com.lncosie.kab.sql.Table
import com.lncosie.kab.sql.View

@TableName("device")
open class Device() : Table() {
    @Column()
    public @JvmField var name: String? = null
    @Column()
    public @JvmField var passwrod: String? = null
    @Column()
    public @JvmField var gateway_id: Int = 0
    @Column()
    public @JvmField var device_uid: String? = null

    fun users(): Iterator<User> {
        assert(id != null, { "device do not init" })
        return View.where<User>("device_uid=?", id!!);
    }

    companion object {
        var idOfDeviceConnected: Int? = null
        fun usersOfconnected(): Iterator<User> {
            if (idOfDeviceConnected != null)
                return View.where<User>("device_uid=?", idOfDeviceConnected!!)
            else
                return arrayOf<User>().iterator()
        }

        fun historysOfconnected(): Iterator<User> {
            if (idOfDeviceConnected != null)
                return View.where<User>("device_uid=?", idOfDeviceConnected!!)
            else
                return arrayOf<User>().iterator()
        }
    }
}