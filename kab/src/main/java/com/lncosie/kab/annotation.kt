package com.lncosie.kab
import com.lncosie.kab.injector.*
import kotlin.annotation.AnnotationRetention.RUNTIME
import kotlin.annotation.AnnotationTarget.*
import kotlin.reflect.KClass

/***
 * meta annotation
 */
@Retention(RUNTIME)
@Target(ANNOTATION_CLASS)
annotation class Binding(val value: KClass<*>)

@Retention(RUNTIME)
@Target(CLASS)
public annotation class TableName(val value: String)

@Retention(RUNTIME)
@Target(CLASS)
public annotation class ViewName(val value: String, val viewAsSelect: String)

@Retention(RUNTIME)
@Target(FIELD)
public annotation class Column()

@Retention(RUNTIME)
@Target(FIELD)
public annotation class NotNull()

@Retention(RUNTIME)
@Target(FIELD)
public annotation class Unique()

@Retention(RUNTIME)
@Target(FIELD)
public annotation class PrimaryKey()

/***------------------------***/


@Retention(RUNTIME)
@Target(FIELD)
@Binding(ViewBinder::class)
annotation class Dataview(val value: String)


/***
 * meta annotation
 */
@Retention(RUNTIME)
@Target(CLASS)
annotation class Named(val value: String)

/***
 * annotation on android field
 */
@Retention(RUNTIME)
@Target(FIELD)
@Binding(InjectBinder::class)
annotation class Inject(vararg val value: String)


enum class ThreadMode{
    UI,WORKER,ANY
}

/***------------------------***/
@Retention(RUNTIME)
@Target(FUNCTION)
@Binding(BusDispatchBinder::class)
annotation class OnMessage(val value:ThreadMode=ThreadMode.UI)


/***
 * annotation on android controls
 */
@Retention(RUNTIME)
@Target(CLASS)
annotation class Layout(val value: Int)


/***
 * annotation on android controls
 */
@Retention(RUNTIME)
@Target(FIELD)
@Binding(IdBinder::class)
annotation class Bind(val value: Int)


/***
 * must annotation on
 * fun():Unit
 */
@Retention(RUNTIME)
@Target(FUNCTION)
@Binding(ClickBinder::class)
public annotation class OnClick(vararg val value: Int)

/***
 * must annotation on
 * fun():Boolean
 */
@Retention(RUNTIME)
@Target(FUNCTION)
@Binding(LongClickBinder::class)
public annotation class OnLongClick(vararg val value: Int)

/***
 * must annotation on
 * fun(Int):Unit
 */
@Retention(RUNTIME)
@Target(FUNCTION)
@Binding(ItemClickBinder::class)
public annotation class OnItemClick(vararg val value: Int)


/***
 * must annotation on
 * TextBinding
 */
@Retention(RUNTIME)
@Target(FIELD)
@Binding(TextBinder::class)
public annotation class BindText(val value: Int)


/***
 * must annotation on
 * EditBinding
 */
@Retention(RUNTIME)
@Target(FIELD)
@Binding(EditBinder::class)
public annotation class BindEdit(val value: Int)