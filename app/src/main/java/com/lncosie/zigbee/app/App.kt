package com.lncosie.zigbee.app

import android.app.Application
import com.lncosie.kab.Kab
import com.lncosie.zigbee.model.Device
import com.lncosie.zigbee.model.Gateway
import com.lncosie.zigbee.model.History
import com.lncosie.zigbee.model.User


class App : Application() {
    override fun onCreate() {
        super.onCreate()



        Kab.init(this, "db", 1)
        val g = Gateway()
        g.name = "g";
        g.save()
        val d = Device()
        d.name = "g";
        d.save()
        val u = User()
        u.name = "g";
        u.save()
        val h = History()
        h.time = 1512241100
        h.auth_id = 1
        u.save()


    }
}

