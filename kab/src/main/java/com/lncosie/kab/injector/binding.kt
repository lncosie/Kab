package com.lncosie.kab.injector

import android.database.sqlite.SQLiteDatatypeMismatchException
import android.widget.AdapterView
import android.widget.EditText
import com.lncosie.kab.ThreadMode
import com.lncosie.kab.rx.RxBus
import com.lncosie.kab.rx.TypedPublisher
import com.lncosie.kab.sql.DataView
import com.lncosie.kab.sql.View
import java.lang.reflect.Field
import java.lang.reflect.Method
import java.lang.reflect.ParameterizedType

interface IBinder {
    fun instance(self:Any,context: Any, target: Any, id: Any): Any
    fun clearup(self:Any,context: Any,id: Any): Any{
        throw NotImplementedError()
    }
}


class IdBinder : IBinder {
    override fun instance(self:Any,context: Any, target: Any, id: Any): Any {
        val findById = context.javaClass.getMethod("findViewById", Int::class.java);
        return findById.invoke(context, id)
    }
}

class TextBinder : IBinder {
    override fun instance(self:Any,context: Any, target: Any, id: Any): Any {
        val findById = context.javaClass.getMethod("findViewById", Int::class.java);
        val control = findById.invoke(context, id)
        val get = fun():String{
            val getter=control.javaClass.getMethod("getText")
            return getter.invoke(control).toString();
        }
        val set = fun(value:String){
            val setter=control.javaClass.getMethod("setText",CharSequence::class.java)
            setter.invoke(control,value);
        }
        val binding = (target as Field).type.newInstance() as AbstractBinder<String>;
        binding.context = BinderContext<String>(control, get, set)
        return binding;
    }
}

class EditBinder : IBinder {
    override fun instance(self:Any,context: Any, target: Any, id: Any): Any {
        val findById = context.javaClass.getMethod("findViewById", Int::class.java);
        val control = findById.invoke(context, id)
        val get = fun():String{
            val getter=control.javaClass.getMethod("getText")
            return getter.invoke(control).toString();
        }
        val set = fun(value:String){
            val setter=control.javaClass.getMethod("setText",CharSequence::class.java)
            setter.invoke(control,value);
        }
        val binding = (target as Field).type.newInstance() as AbstractBinder<String>;
        binding.context = BinderContext(control, get, set)
        return binding;
    }
}
class ClickBinder : IBinder {
    override fun instance(self:Any,context: Any, target: Any, ids: Any): Any {
        val findById = context.javaClass.getMethod("findViewById", Int::class.java);
        val ints=ids as IntArray
        val click = object : android.view.View.OnClickListener {
            override fun onClick(v: android.view.View?) {
                val method = target as Method;
                method.isAccessible = true
                method.invoke(self, v)
            }
        }
        for(id in ints){
            val control = findById.invoke(context, id)
            val set = control.javaClass.getMethod("setOnClickListener", android.view.View.OnClickListener::class.java)
            set.invoke(control, click)
        }
        return self;
    }
}

class ItemClickBinder : IBinder {
    override fun instance(self:Any,context: Any, target: Any, ids: Any): Any {
        val findById = context.javaClass.getMethod("findViewById", Int::class.java);
        val ints=ids as IntArray
        val click = object : AdapterView.OnItemClickListener {
            override fun onItemClick(parent: AdapterView<*>?, view: android.view.View?, position: Int, id: Long) {
                val method = target as Method;
                method.isAccessible = true
                method.invoke(self, position)
            }

        }

        for(id in ints) {
            val control = findById.invoke(context, id)
            val set = control.javaClass.getMethod("setOnItemClickListener", AdapterView.OnItemClickListener::class.java)
            set.invoke(control, click)
        }
        return self;
    }
}

class LongClickBinder : IBinder {
    override fun instance(self:Any,context: Any, target: Any, ids: Any): Any {
        val findById = context.javaClass.getMethod("findViewById", Int::class.java);
        val ints=ids as IntArray
        val click = object : android.view.View.OnLongClickListener {
            override fun onLongClick(v: android.view.View?): Boolean {
                val method = target as Method;
                method.isAccessible = true
                return method.invoke(self, v) as Boolean
            }
        }
        for(id in ints) {
            val control = findById.invoke(context, id)
            val set = control.javaClass.getMethod("setOnLongClickListener", android.view.View.OnLongClickListener::class.java)
            set.invoke(control, click)
        }
        return self;
    }
}


data class BinderContext<T>(val ref: Any, val get: ()->T, val set: (T)->Unit)




class ViewBinder : IBinder {
    override fun instance(self:Any,context: Any, target: Any, sql: Any): Any {
        val field = target as Field;
        if (field.type.equals(DataView::class.java)) {
            val generic = field.genericType as? ParameterizedType
            if (generic != null) {
                if (generic.actualTypeArguments.size == 1) {
                    val param = generic.actualTypeArguments.get(0) as Class<View>;
                    return DataView<View>(param, sql as String)
                }
            }
        }
        throw SQLiteDatatypeMismatchException()
    }
}

class InjectBinder : IBinder {
    override fun instance(self:Any,context: Any, target: Any, name: Any): Any {
        return Module.inject((target as Field).type, name);
    }
}
class BusDispatchBinder:IBinder{

    override fun instance(self:Any,context: Any, target: Any, mode: Any): Any {
        val method=target as Method
        if(method.parameterTypes.size!=1)
            throw IllegalArgumentException()
        val type=method.parameterTypes.get(0)
        val dispatcher=RxBus.dispatchers.getOrPut(type,{TypedPublisher(type)})
        dispatcher.add(TypedPublisher.CallSite(self,method,mode as ThreadMode))
        return dispatcher
    }
    override fun clearup(self:Any,target: Any, id: Any): Any{
        val method=target as Method
        if(method.parameterTypes.size!=1)
            throw IllegalArgumentException()
        val type=method.parameterTypes.get(0)
        val dispatcher=RxBus.dispatchers.getOrPut(type,{TypedPublisher(type)})
        dispatcher.dropWhile { it.method.equals(target) }
        return dispatcher
    }

}

open class AbstractBinder<T>() {
    lateinit var context: BinderContext<T>
    var value: T
        get() = context.get() as T
        set(value) {
            context.set(value)
        }

    override fun toString(): String {
        return value.toString()
    }
}