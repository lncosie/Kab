package com.lncosie.kab.injector

import android.content.Context
import com.lncosie.kab.Kab
import com.lncosie.kab.Named
import com.lncosie.kab.TableName
import com.lncosie.kab.ViewName
import com.lncosie.kab.sql.TableInfo
import dalvik.system.DexFile
import java.util.*

class Module {
    companion object {
        fun scanForModel(application: Context) {
            val path = application.getPackageCodePath()
            val loader = application.getClassLoader()
            val file = DexFile(path)
            val sqlScript = arrayListOf<String>()
            for (code in file.entries()) {
                if(!code.startsWith("com.lncosie"))
                    continue

                val classT = file.loadClass(code, loader)
                var viewT = classT?.getAnnotation<ViewName>(ViewName::class.java)
                if (viewT != null) {
                    val viewDef = TableInfo(classT)
                    viewDef.tableInit(null)
                    sqlScript.add(viewDef.getSchema())
                    continue
                }
                var tableT = classT?.getAnnotation<TableName>(TableName::class.java)
                if (tableT != null) {
                    val tableDef = TableInfo(classT)
                    tableDef.tableInit(null)
                    sqlScript.add(0, tableDef.getSchema())
                }
                var namedT = classT?.getAnnotation<Named>(Named::class.java)
                if (namedT != null) {
                    val named = namedT as Named;
                    injMap.set(named.value, classT!!);
                }
            }
            try {
                Kab.sql.beginTranaction()
                sqlScript.forEach { Kab.sql.execSQL(it) }
                Kab.sql.setTransactionSuccessful()
            } catch(e: Exception) {
                print(e)
            } finally {
                Kab.sql.endTranaction()
            }
        }

        private val injMap = HashMap<String, Class<*>>()

        fun inject(target: Class<*>, name: Any): Any {
            var concreate = injMap.get(name)
            if (concreate == null)
                concreate = target;
            return concreate.newInstance()
        }
    }
}