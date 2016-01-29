package com.lncosie.kab.injector

import com.lncosie.kab.*
import java.lang.reflect.Field
import java.lang.reflect.Method


class Injector {
    companion object {
        public fun unbind(self: Any){
            self.javaClass.declaredMethods.forEach {
                method ->method.annotations.forEach {
                    ann ->when (ann) {
                        is OnMessage->busUnbind<OnMessage>(self, method, ann.value)
            }
                }
            }
        }
        public fun bind(self: Any, context: Any) {
                self.javaClass.declaredFields.forEach {
                field ->
                field.annotations.forEach {
                    ann ->when (ann) {
                        is Bind -> bindIntId<Bind>(self, field, ann.value, context)
                        is BindText -> bindIntId<BindText>(self, field, ann.value, context)
                        is Inject -> inject<Inject>(self, field, ann.value , context)
                        is Dataview -> bindIntId<Dataview>(self, field, ann.value, context)
                    }
                }
            }
            self.javaClass.declaredMethods.forEach {
                method ->
                method.annotations.forEach {
                    ann ->when (ann) {
                        is OnMessage->busBind<OnMessage>(self, method, ann.value, context)
                        is OnClick -> bindEvent<OnClick>(self, method, ann.value, context)
                        is OnLongClick -> bindEvent<OnLongClick>(self, method, ann.value, context)
                        is OnItemClick -> bindEvent<OnItemClick>(self, method, ann.value, context)
                    }

                }
            }
        }

        /***
         * filed inject, value is int such as id
         */
        private inline fun<reified T : Any> bindIntId(self: Any, field: Field, value: Int, context: Any) {
            val binding = T::class.java.getAnnotation(Binding::class.java)
            val binder = binding.value.java.newInstance() as IBinder;
            val control = binder.instance(self,context, field, value);
            field.isAccessible = true
            field.set(self, control)
        }


        /***
         * filed inject, value is string
         */
        private inline fun<reified T : Any> inject(self: Any, field: Field, value: Array<out String>, context: Any) {
            if(value.size==1){
                return bindIntId<T>(self,field,value.get(0),context)
            }else{
                val binding = T::class.java.getAnnotation(Binding::class.java)
                val binder = binding.value.java.newInstance() as IBinder;
                val array=field.type as Class<*>
                val param=array.componentType
                val obj = java.lang.reflect.Array.newInstance(param,value.size) as Array<Any>
                value.forEachIndexed { i, s ->
                    obj.set(i,binder.instance(self,context, field, value.get(i)))
                }
                field.isAccessible = true
                field.set(self, obj)
            }
        }
        private inline fun<reified T : Any> bindIntId(self: Any, field: Field, value: String, context: Any) {
            val binding = T::class.java.getAnnotation(Binding::class.java)
            val binder = binding.value.java.newInstance() as IBinder;
            val obj = binder.instance(self,context, field, value);
            field.isAccessible = true
            field.set(self, obj)
        }


        /***
         * method inject,value is int
         */
        private inline fun<reified T : Any> bindEvent(self: Any, method: Method, value: IntArray, context: Any) {
            val binding = T::class.java.getAnnotation(Binding::class.java)
            val binder = binding.value.java.newInstance() as IBinder;
            binder.instance(self,context, method, value);
        }
        /***
         * event inject,value is threadmode
         */
        private inline fun<reified T : Any> busBind(self: Any, method: Method, value: ThreadMode, context: Any) {
            val binding = T::class.java.getAnnotation(Binding::class.java)
            val binder = binding.value.java.newInstance() as IBinder;
            binder.instance(self,context, method, value);
        }
        /***
         * event inject,value is threadmode
         */
        private inline fun<reified T : Any> busUnbind(self: Any, method: Method, value: ThreadMode) {
            val binding = T::class.java.getAnnotation(Binding::class.java)
            val binder = binding.value.java.newInstance() as IBinder;
            binder.clearup(self,method, value);
        }
    }
}

