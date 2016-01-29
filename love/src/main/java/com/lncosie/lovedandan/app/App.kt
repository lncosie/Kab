package com.lncosie.lovedandan.app

import android.app.Application
import com.lncosie.kab.Kab
import com.lncosie.lovedandan.model.ChatInterface

class App :Application(){
    override fun onCreate() {
        super.onCreate()
        Kab.init(this,"db",1)
    }
}
