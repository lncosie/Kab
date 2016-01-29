package com.lncosie.zigbee.model

import com.lncosie.kab.Column
import com.lncosie.kab.TableName
import com.lncosie.kab.sql.Table
import com.lncosie.kab.sql.View

@TableName("setting")
class Setting : Table() {
    @Column()
    public @JvmField var name: String? = null
    @Column()
    public @JvmField var value: String? = null

    companion object {
        fun set(name: String, value: String?) {
            val it = View.where<Setting>("name", name);
            var config: Setting? = null
            if (it.hasNext()) {
                config = it.next()
            }
            if (config == null) {
                if (value == null)
                    return
                config = Setting()
            }
            if (value == null) {
                config.delete()
            } else {
                config.name = name
                config.value = value
                config.save()
            }

        }

        fun get(name: String): String? {
            val it = View.where<Setting>("name", name);
            if (it.hasNext()) {
                return it.next().value
            }
            return null
        }
    }
}