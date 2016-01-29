package com.lncosie.kab

import android.content.Context
import com.lncosie.kab.injector.Injector
import com.lncosie.kab.injector.Module
import com.lncosie.kab.rx.RxBus
import com.lncosie.kab.sql.SqliteDriver

public open class Kab {
    companion object {
       public fun init(context: Context, name: String, version: Int) {
            sqlite = SqliteDriver(context, name, version);
            Module.scanForModel(context)
        }
        public fun bind(self: Any, context: Any) {
            Injector.bind(self, context)
        }
        public fun unbind(self: Any) {
            Injector.unbind(self)
        }

        private lateinit var sqlite: SqliteDriver

        val sql: SqliteDriver
            get() = sqlite
        val bus: RxBus
            get() = RxBus
    }
}
