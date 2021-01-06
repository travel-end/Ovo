package hhh.qaq.ovo.data

import com.franmontiel.persistentcookiejar.ClearableCookieJar
import com.franmontiel.persistentcookiejar.PersistentCookieJar
import com.franmontiel.persistentcookiejar.cache.SetCookieCache
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor
import com.google.gson.GsonBuilder
import hhh.qaq.ovo.BuildConfig
import hhh.qaq.ovo.R
import hhh.qaq.ovo.app.OvoApp
import hhh.qaq.ovo.data.gson.MyGsonConverterFactory
import hhh.qaq.ovo.listener.RequestCallback
import hhh.qaq.ovo.utils.getResString
import hhh.qaq.ovo.utils.isNetworkAvailable
import hhh.qaq.ovo.utils.log
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.json.JSONException
import org.json.JSONObject
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import java.io.File
import java.io.IOException
import java.util.concurrent.TimeUnit

class NetEaseRetrofitClient private constructor() {
    /**
     * 获取Service
     *
     * @param clazz
     * @param <T>
     * @return
    </T> */
    fun <T> create(clazz: Class<T>, baseUrl: String): T {
        val retrofit = Retrofit.Builder().baseUrl(baseUrl)
            .client(okHttpClient!!)
            .addConverterFactory(MyGsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
        return retrofit.create(clazz)
    }

    companion object {
        private var mNeRetrofitClient: NetEaseRetrofitClient? = null
        private const val CONNECT_TIMEOUT = 60L
        private const val READ_TIMEOUT = 10L
        private const val WRITE_TIMEOUT = 10L

        //设缓存有效期为1天
        private const val CACHE_STALE_SEC = 60 * 60 * 24 * 1.toLong()

        //查询缓存的Cache-Control设置，为if-only-cache时只查询缓存而不会请求服务器，max-stale可以配合设置缓存失效时间
        const val CACHE_CONTROL_CACHE =
            "only-if-cached, max-stale=$CACHE_STALE_SEC"

        //查询网络的Cache-Control设置
        //(假如请求了服务器并在a时刻返回响应结果，则在max-age规定的秒数内，浏览器将不会发送对应的请求到服务器，数据由缓存直接返回)
        const val CACHE_CONTROL_NETWORK = "Cache-Control: public, max-age=10"

        // 避免出现 HTTP 403 Forbidden，参考：http://stackoverflow.com/questions/13670692/403-forbidden-with-java-but-not-web-browser
        private const val AVOID_HTTP403_FORBIDDEN =
            "User-Agent: Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11"

        @Volatile
        private var mOkHttpClient: OkHttpClient? = null

        /**
         * 获取OkHttpClient实例
         *
         * @return
         */
        private val okHttpClient: OkHttpClient?
            get() {
                if (mOkHttpClient == null) {
                    // Cookie 持久化
                    val cookieJar: ClearableCookieJar = PersistentCookieJar(
                        SetCookieCache(),
                        SharedPrefsCookiePersistor(OvoApp.getInstance())
                    )
                    synchronized(NetEaseRetrofitClient::class.java) {
                        val cache = Cache(
                            File(
                                OvoApp.getInstance().cacheDir,
                                "HttpCache"
                            ), 1024 * 1024 * 100
                        )
                        if (mOkHttpClient == null) {
                            val builder = OkHttpClient.Builder()

                            //设置http 日志拦截
                            if (BuildConfig.DEBUG) {
                                val httpLogging = HttpLoggingInterceptor()
                                httpLogging.setLevel(HttpLoggingInterceptor.Level.BODY)
                                builder.addInterceptor(httpLogging)
                            }
                            mOkHttpClient = builder.cache(cache)
                                .connectTimeout(
                                    CONNECT_TIMEOUT,
                                    TimeUnit.SECONDS
                                )
                                .readTimeout(
                                    READ_TIMEOUT,
                                    TimeUnit.SECONDS
                                )
                                .writeTimeout(
                                    WRITE_TIMEOUT,
                                    TimeUnit.SECONDS
                                )
                                .cookieJar(cookieJar)
                                .build()
                        }
                    }
                }
                return mOkHttpClient
            }

        //获取NetEaseRetrofitClient的单例
        val instance: NetEaseRetrofitClient
            get() {
                if (mNeRetrofitClient == null) {
                    synchronized(NetEaseRetrofitClient::class.java) {
                        if (mNeRetrofitClient == null) {
                            mNeRetrofitClient = NetEaseRetrofitClient()
                        }
                    }
                }
                return mNeRetrofitClient!!
            }

        /**
         * 发送网络请求
         */
        fun <T> request(
            service: Observable<T>,
            result: RequestCallback<T>?
        ) {
            if (!isNetworkAvailable(OvoApp.getInstance())) {
                result?.error(OvoApp.getInstance().getString(R.string.net_error))
                return
            }
            service.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<T> {
                    override fun onSubscribe(d: Disposable) {
                    }

                    override fun onComplete() {

                    }

                    override fun onNext(t: T) {
                        "RxJava onNext：$t".log("JG")
                        result?.success(t)
                    }

                    override fun onError(e: Throwable) {
                        "RxJava onError：${e.message}".log("JG")
                        if (e is HttpException) {
                            var errorInfo: String? = null
                            try {
                                errorInfo = e.response()?.errorBody()?.string()
                                errorInfo?.let {
                                    val jsonObj = JSONObject(it)
                                    result?.error(jsonObj.getString("msg"))
                                }
                            } catch (e: Exception) {
                                e.printStackTrace()
                                if (errorInfo?.contains("LeanEngine") == true) {
                                    errorInfo = "默认LeanEngine服务器请求超出限制\n请稍后再试！"
                                }
                                if (e is JSONException) {
                                    result?.error(errorInfo ?: R.string.net_error.getResString())
                                } else {
                                    result?.error(R.string.net_error.getResString())
                                }
                            }
                        } else {
                            result?.let {
                                if (e.message == null) {
                                    it.error(R.string.net_error.getResString())
                                } else {
                                    it.error(e.message!!)
                                }
                            }
                        }
                    }
                })
        }
    }
}