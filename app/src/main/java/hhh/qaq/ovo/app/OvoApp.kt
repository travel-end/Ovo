package hhh.qaq.ovo.app

import androidx.multidex.MultiDexApplication
import com.danikula.videocache.HttpProxyCacheServer
import hhh.qaq.ovo.delegate.CacheFileNameGenerator
import hhh.qaq.ovo.utils.FileUtils
import io.reactivex.plugins.RxJavaPlugins
import org.litepal.LitePal
import java.io.File

/**
 * @By Journey 2020/12/25
 * @Description
 */
class OvoApp:MultiDexApplication() {
    private var proxy: HttpProxyCacheServer?=null
    companion object {
        private var instances: OvoApp?=null
        fun getInstance(): OvoApp {
            if (instances == null) {
                synchronized(OvoApp::class.java) {
                    if (instances == null) {
                        instances =
                            OvoApp()
                    }
                }
            }
            return instances!!
        }
        fun getProxy(): HttpProxyCacheServer {
            return if (getInstance().proxy == null) {
                getInstance().proxy = getInstance()
                    .newProxy()
                getInstance().proxy!!
            } else {
                getInstance().proxy!!
            }
        }
    }
    override fun onCreate() {
        super.onCreate()
        instances = this
        LitePal.initialize(this)
        setRxJavaErrorHandler()
    }
    private fun newProxy(): HttpProxyCacheServer {
        return HttpProxyCacheServer
                .Builder(this)
                .cacheDirectory(File(FileUtils.getMusicCacheDir()!!))
                .fileNameGenerator(CacheFileNameGenerator())
                .build()
    }
    private fun setRxJavaErrorHandler() {
        RxJavaPlugins.setErrorHandler {
            it?.printStackTrace()
        }
    }
}