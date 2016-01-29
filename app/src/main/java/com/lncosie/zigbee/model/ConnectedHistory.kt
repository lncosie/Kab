package com.lncosie.zigbee.model

import com.lncosie.kab.Column
import com.lncosie.kab.sql.View

class ConnectedHistory : View() {
    @Column()
    public @JvmField var auth_id: Int = 0
    @Column()
    public @JvmField var time: Int = 0

    @Column()
    public @JvmField var name: String? = null
    @Column()
    public @JvmField var user_id: Int = 0
    @Column()
    public @JvmField var image: Array<Byte>? = null
}