package com.lncosie.kab.sql

import android.content.ContentValues
import android.database.Cursor
import com.lncosie.kab.*
import java.lang.reflect.Field
import java.util.*
import kotlin.properties.Delegates


class TableInfo(val m: Class<*>) {
    public var tableName: String by Delegates.notNull()
    val columns = ArrayList<ColumnInfo>()
    var complete = false

    init {
        val table = m.getAnnotation(TableName::class.java)
        tableName = table?.value ?: (m.getAnnotation(ViewName::class.java)?.value?:"null")
    }

    companion object {
        val infoMap = hashMapOf<Class<*>, TableInfo>()
        public fun get(classT: Class<*>): TableInfo {
            return infoMap.getOrPut(classT, {
                TableInfo(classT)
            }
            )
        }
    }

    public fun tableInit(cursor: Cursor?) {
        if (complete)
            return
        if (cursor == null && columns.size > 0) {
            return
        } else if (cursor != null)
            complete = true
        columns.clear()
        m.fields.filter {
            filter(it)
        }.forEach {
            val name = it.getName().toLowerCase()
            val index = cursor?.getColumnIndex(name) ?: 0
            if(index>=0)
                columns.add(ColumnInfo(it, name, index))
        }
    }

    private inline fun filter(field: Field): Boolean {
        return field.getAnnotation(Column::class.java) != null ||
                field.getAnnotation(Unique::class.java) != null ||
                field.getAnnotation(PrimaryKey::class.java) != null ||
                field.getAnnotation(NotNull::class.java) != null
    }

    fun isTable(): Boolean {
        return (m.getAnnotation(TableName::class.java) != null)
    }

    fun isView(): Boolean {
        return (m.getAnnotation(ViewName::class.java) != null)
    }

    fun restrict(field: Field, type: Int): String {
        val restrictsql = StringBuffer()
        field.getDeclaredAnnotations().forEach {
            restrictsql.append(
                    when (type) {
                        1 -> " INTEGER"
                        2 -> " INTEGER"
                        3 -> " REAL"
                        4 -> " REAL"
                        5 -> " TEXT"
                        6 -> " BLOB"
                        7 -> " INTEGER"
                        8 -> " INTEGER"
                        9 -> " INTEGER"
                        10 -> " TEXT"
                        else -> ""
                    }
            )
            restrictsql.append(
                    when (it) {
                        is NotNull -> " Not NULL "
                        is PrimaryKey -> "  PRIMARY KEY "
                        is Unique -> " Unique "
                        else -> ""
                    }
            )
        }
        return restrictsql.toString()
    }

    public fun getSchema(): String {
        if (isTable()) {
            val sql = StringBuilder()
            sql.append("CREATE TABLE IF NOT EXISTS  ")
            sql.append(tableName)
            sql.append(columns.map {
                it.name + restrict(it.field, it.type)
            }.joinToString().let { "(" + it + ")" })
            return sql.toString().toLowerCase()
        } else {
            val view = m.getAnnotation(ViewName::class.java)
            val sql = StringBuilder()
            sql.append("CREATE VIEW IF NOT EXISTS ")
            sql.append(tableName)
            sql.append(" AS ")
            sql.append(view.viewAsSelect)
            return sql.toString().toLowerCase()
        }
    }


    fun<M : View> import(cursor: Cursor): M {
        val model = m.newInstance() as M
        for (column in columns) {
            column.invoke(model, cursor)
        }
        return model
    }

    fun export(m: Any, values: ContentValues) {
        //values.put("Id", m.Id)
        for (column in columns) {
            when (column.type) {
                1 -> values.put(column.name, column.field.get(m) as? Int)
                2 -> values.put(column.name, column.field.get(m) as? Long)
                3 -> values.put(column.name, column.field.get(m) as Float)
                4 -> values.put(column.name, column.field.get(m) as Double)
                5 -> values.put(column.name, column.field.get(m) as String?)
                6 -> values.put(column.name, column.field.get(m) as? ByteArray)
                7 -> values.put(column.name, column.field.get(m) as Short)
                8 -> values.put(column.name, column.field.get(m) as Byte)
                9 -> values.put(column.name, column.field.get(m) as Boolean)
                10 -> values.put(column.name, (column.field.get(m) as Date).toString())
                else -> values.putNull(column.name)
            }
        }
    }

    inner class ColumnInfo {
        var field: Field by Delegates.notNull()
        var type: Int = 0;
        var index: Int = 0;
        var name: String by Delegates.notNull()

        constructor(field: Field, name: String, index: Int) {
            field.setAccessible(true)
            this.field = field
            this.index = index
            this.name = name
            type = when (field.type) {

                Int::class.java -> 1
                Integer::class.java -> 1
                Long::class.java -> 2
                java.lang.Long::class.java -> 2
                Float::class.java -> 3
                Double::class.java -> 4
                String::class.java -> 5
                ByteArray::class.java -> 6
                Short::class.java -> 7
                Byte::class.java -> 8
                Boolean::class.java -> 9
                Date::class.java -> 10
                else -> -1
            }
        }


        fun invoke(m: Any, cursor: Cursor) {
            when (type) {
                1 -> field.set(m, cursor.getInt(index));
                2 -> field.set(m, cursor.getLong(index));
                3 -> field.set(m, cursor.getFloat(index));
                4 -> field.set(m, cursor.getDouble(index));
                5 -> field.set(m, cursor.getString(index));
                6 -> field.set(m, cursor.getBlob(index));
                7 -> field.set(m, cursor.getShort(index));
                8 -> field.set(m, cursor.getInt(index));
                9 -> field.set(m, cursor.getInt(index) == 0);
                10 -> field.set(m, Date(cursor.getString(index)));
            }
        }
    }
}

