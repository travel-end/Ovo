package hhh.qaq.ovo.listener

/**
 * @By Journey 2021/1/6
 * @Description
 */
interface RequestCallback<T> {
    fun success(result:T)
    fun error(msg:String)
}