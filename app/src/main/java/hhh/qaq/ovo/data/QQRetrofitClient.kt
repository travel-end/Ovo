package hhh.qaq.ovo.data

import com.franmontiel.persistentcookiejar.PersistentCookieJar
import com.franmontiel.persistentcookiejar.cache.SetCookieCache
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor
import com.google.gson.GsonBuilder
import hhh.qaq.ovo.BuildConfig
import hhh.qaq.ovo.app.OvoApp
import hhh.qaq.ovo.constant.Constant
import hhh.qaq.ovo.data.gson.MyGsonConverterFactory
import hhh.qaq.ovo.utils.log
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit


/**
 * @By Journey 2020/9/26
 * @Description
 */
class QQRetrofitClient {
    companion object {
        private var mInstance: QQRetrofitClient? = null
        @Volatile
        private var mOkHttpClient: OkHttpClient? = null
        fun getInstance(): QQRetrofitClient {
            if (mInstance == null) {
                synchronized(this) {
                    mInstance = QQRetrofitClient()
                }
            }
            return mInstance!!
        }

        private fun getNetEaseOkHttpClient():OkHttpClient {
            if (mOkHttpClient == null) {
                val cookieJar = PersistentCookieJar(SetCookieCache(),SharedPrefsCookiePersistor(OvoApp.getInstance()))
                synchronized(this) {
                    val cache = Cache(File(OvoApp.getInstance().cacheDir,"HttpCache"),1024 * 1024 * 100)
                    if (mOkHttpClient==null) {
                        val builder = OkHttpClient.Builder()
                        if (BuildConfig.DEBUG) {
                            builder.addInterceptor(getLoggerInterceptor())
                        }
                        mOkHttpClient = builder
                            .cache(cache)
                            .connectTimeout(60L,TimeUnit.SECONDS)
                            .readTimeout(10L,TimeUnit.SECONDS)
                            .writeTimeout(10L,TimeUnit.SECONDS)
                            .cookieJar(cookieJar)
                            .build()
                    }
                }
            }
            return mOkHttpClient!!
        }

        private fun getLoggerInterceptor(): HttpLoggingInterceptor {
            val httpLoggingInterceptor = HttpLoggingInterceptor(ILoggerInterceptor())
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            return httpLoggingInterceptor
        }
    }

    private val okHttpClient = OkHttpClient.Builder()
        .callTimeout(80, TimeUnit.SECONDS)
        .writeTimeout(80, TimeUnit.SECONDS)
        .addNetworkInterceptor(getLoggerInterceptor())
        .build()




    class ILoggerInterceptor : HttpLoggingInterceptor.Logger {
        override fun log(message: String) {
            message.log("RetrofitClient")
        }
    }


    private val retrofit = Retrofit.Builder()
        .client(okHttpClient)
        .baseUrl(Constant.FIDDLER_BASE_QQ_URL)
        .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()

    // 获取歌手照片
    private val singerRetrofit = Retrofit.Builder()
        .client(okHttpClient)
        .baseUrl(Constant.SINGER_PIC_BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()

    // 得到播放地址
    private val songUrlRetrofit = Retrofit.Builder()
        .client(okHttpClient)
        .baseUrl(Constant.FIDDLER_BASE_SONG_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()

    private val poetryRetrofit = Retrofit.Builder()
        .client(okHttpClient)
        .baseUrl(Constant.GUSHI_BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()

    private val netEaseRetrofit = Retrofit.Builder()
        .baseUrl(Constant.BASE_NETEASE_URL)
        .client(getNetEaseOkHttpClient())
        .addConverterFactory(MyGsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()


    val apiService: ApiService = retrofit.create(ApiService::class.java)

    val singerApiService: ApiService = singerRetrofit.create(ApiService::class.java)

    val songUrlApiService: ApiService = songUrlRetrofit.create(ApiService::class.java)

    val poetryApiService: ApiService = poetryRetrofit.create(ApiService::class.java)

    val netEaseApiService: ApiService = netEaseRetrofit.create(ApiService::class.java)

}