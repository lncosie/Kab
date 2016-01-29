package com.lncosie.kab.sql

import com.lncosie.kab.Kab
import com.lncosie.kab.PrimaryKey


public open class View {
    @PrimaryKey
    public @JvmField var id: Long? = null;
    public companion object {
        public final inline fun <reified M : View> load(id: Long): M? {
            return Kab.sql.load<M>(id)
        }

        public final inline fun <reified M : View> all(): Iterator<M> {
            return Kab.sql.all<M>()
        }

        public final inline fun <reified M : View> where(sql: String, vararg args: Any): Iterator<M> {
            return Kab.sql.where<M>(sql, args)
        }
    }
}




