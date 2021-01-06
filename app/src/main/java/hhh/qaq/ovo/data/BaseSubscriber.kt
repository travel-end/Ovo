//package hhh.qaq.ovo.data
//
//import hhh.qaq.ovo.listener.RequestCallback
//import io.reactivex.observers.DisposableObserver
//
//class BaseSubscriber<T> constructor(private val requestCallback: RequestCallback<T>?) : DisposableObserver<T>() {
//    override fun onComplete() {
//    }
//
//    override fun onNext(t: T) {
//        requestCallback?.success(t)
//    }
//
//    override fun onError(e: Throwable) {
//        requestCallback?.let {
//            val message = e.localizedMessage
//            var finalMsg = if (message.isNullOrBlank()) "Error" else message
//
//        }
//    }
//}