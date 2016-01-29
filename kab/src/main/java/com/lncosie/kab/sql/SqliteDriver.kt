package com.lncosie.kab.sql

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.lncosie.kab.Kab
import com.lncosie.kab.event.TableChanged

public class SqliteDriver(context: Context, name: String, version: Int)
: SQLiteOpenHelper(context, name, null, version) {
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        throw UnsupportedOperationException()
    }

    override fun onCreate(db: SQLiteDatabase) {
    }

    public fun beginTranaction() {
        getWritableDatabase().beginTransaction()
    }

    public fun setTransactionSuccessful() {
        getWritableDatabase().setTransactionSuccessful()
    }

    public fun endTranaction() {
        getWritableDatabase().endTransaction()
    }

    public fun execSQL(sql: String) {
        getWritableDatabase().execSQL(sql)
    }

    public fun select(typed: Class<*>, sql: String): Iterator<View> {
        val tableInfo = TableInfo.get(typed)
        return rawQuery(tableInfo, sql, null);
    }


    public fun<M : Table> save(m: M) {
        val database = getWritableDatabase()
        val tableInfo = TableInfo.get(m.javaClass)
        val values: ContentValues = ContentValues()
        tableInfo.tableInit(null)
        tableInfo.export(m, values)
        if (m.id == null) {
            values.remove("id")
            m.id = database.insert(tableInfo.tableName, null, values);
            if(m.fireEvent)
                Kab.bus.post(TableChanged(TableChanged.Change.ADD, m))
        } else {
            database.replace(tableInfo.tableName, null, values)
            if(m.fireEvent)
                Kab.bus.post(TableChanged(TableChanged.Change.UPDATE, m))
        }

    }

    public fun<M : Table> delete(m: M) {
        if (m.id == null)
            return;
        val database = getWritableDatabase()
        val tableInfo = TableInfo.get(m.javaClass)
        tableInfo.tableInit(null)
        database.delete(tableInfo.tableName, "id=?", arrayOf(m.id.toString()))
        if(m.fireEvent)
            Kab.bus.post(TableChanged(TableChanged.Change.DELETE, m))
    }

    public inline fun <reified M : View> load(Id: Long): M? {
        val tableInfo = TableInfo.get(M::class.java)
        val sql = StringBuilder().apply { this.append("SELECT * FROM ").append(tableInfo.tableName).append(" WHERE id=").append(Id.toString()) }
        val it = rawQuery<M>(tableInfo, sql.toString(), null);
        for (element in it)
            return element
        return null
    }

    public inline fun <reified M : View> all(): Iterator<M> {
        val tableInfo = TableInfo.get(M::class.java)
        val sql = StringBuilder().apply { this.append("SELECT * FROM ").append(tableInfo.tableName) }
        return rawQuery(tableInfo, sql.toString(), null);
    }

    public inline fun <reified M : View> where(where: String, bound: Array<out Any>): Iterator<M> {
        val tableInfo = TableInfo.get(M::class.java)
        val sql = StringBuilder().apply { this.append("SELECT * FROM ").append(tableInfo.tableName).append(" WHERE ").append(where) }
        return rawQuery(tableInfo, sql.toString(), bound.map { it.toString() }.toTypedArray());
    }

    public fun <M : View> rawQuery(tableInfo: TableInfo, sql: String, selectionArgs: Array<String>?): Iterator<M> {
        val cursor = getReadableDatabase().rawQuery(sql.toLowerCase(), selectionArgs)
        tableInfo.tableInit(cursor)
        val iterator = object : Iterator<M> {
            override fun next(): M {
                return tableInfo.import(cursor)
            }

            override fun hasNext(): Boolean {
                val has=cursor.moveToNext()
                if(!has)
                    cursor.close()
                return has
            }
        }
        return iterator;
    }
}