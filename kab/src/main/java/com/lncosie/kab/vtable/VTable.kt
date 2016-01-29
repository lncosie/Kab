//package com.lncosie.kab.vtable
//
//import android.widget.EditText
//import android.widget.TextView
//import com.lncosie.kab.BindText
//import com.lncosie.kab.rx.*
//import com.lncosie.kab.widget.TextBinding
//import javax.crypto.Mac
//import javax.crypto.spec.SecretKeySpec
//
//abstract class  VTableNotify{
//    fun insert(key:String,vararg values:Any)
//    fun delete(key:String)
//    fun select(key:String,where:String,vararg values:Any)
//    fun update(key:String,vararg values:Any)
//}
//
//class VTable(){
//    fun insert(key:String,vararg values:Any){
//
//    }
//
//    fun delete(key:String){}
//    fun select(key:String,where:String,vararg values:Any){}
//    fun update(key:String,vararg values:Any){}
//}
//
//fun craft(){
//    class s{
//
//        val  secret = SecretKeySpec(key.getBytes(), type);
//        val mac = Mac.getInstance(type);
//        mac.init(secret);
//        byte[] bytes = mac.doFinal(value.getBytes());
//        return bytesToHex(bytes);
//        @bind(table,x)
//        @textbind(id)
//        lateinit var g:TextBinding;
//        init{
//            val binder=T2CBinder(table,"name",id)
//            val binder=C2TBinder(table,"name",id)
//            val binder=TCBinder(table,"name",id)
//        }
//    }
//
//
//    val rx=RxPublisher<Int>()
//    rx.textInto(null as TextView)
//    rx.next(1)
//    rx.map { it.toString() }.map { it.toByteArray() }
//            .send(1).textInto(null as TextView)
//}
//fun<T> ISubscribe<T>.send(cnt: Int): ISubscribe<T> {
//
//    val timeout=object:ISubscribe<T>{
//        override fun publish() {
//
//        }
//        override fun subcrib(subscriber: IPublish<T>): IJoint<T> {
//            throw UnsupportedOperationException()
//        }
//
//    }
//    return timeout
//}
//fun<T> ISubscribe<T>.textInto(v:TextView){
//    val toText=object:ISubscribe<T>{
//        override fun next(iter: T) {
//            v.text=iter.toString()
//        }
//    }
//    this.subcrib(toText)
//}
//
//fun<T> ISubscribe<T>.textInto(v:EditText){
//    val toText=object:ISubscribe<T>{
//        override fun next(iter: T) {
//            v.setText(iter.toString())
//        }
//    }
//    this.subcrib(toText)
//}
//fun<T> ISubscribe<T>.timeout(t:Int){
//    val toText=object:IPublish<T>{
//
//        override fun next(iter: T) {
//
//        }
//
//    }
//    this.subcrib(toText)
//}
