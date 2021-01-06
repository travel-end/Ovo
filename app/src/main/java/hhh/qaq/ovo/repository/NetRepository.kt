package hhh.qaq.ovo.repository

import com.cyl.musicapi.netease.NeteaseApiService
import hhh.qaq.ovo.base.BaseRepository
import hhh.qaq.ovo.constant.Constant
import hhh.qaq.ovo.data.ApiService
import hhh.qaq.ovo.data.NetEaseRetrofitClient
import hhh.qaq.ovo.data.QQRetrofitClient

/**
 * @By Journey 2020/12/25
 * @Description
 */
open class NetRepository:BaseRepository {
    private val intervalTime = 1000 * 60 * 60 * 4//

    protected val apiService by lazy {
        QQRetrofitClient.getInstance().apiService
    }
    protected val singerApiService: ApiService by lazy {
        QQRetrofitClient.getInstance().singerApiService
    }
    protected val songUrlApiService: ApiService by lazy {
        QQRetrofitClient.getInstance().songUrlApiService
    }
    protected val netEaseApiService: ApiService by lazy {
        QQRetrofitClient.getInstance().netEaseApiService
    }
    fun Long?.shouldUpdate() = (System.currentTimeMillis() - (this ?: 0L)) > intervalTime

    protected val netEaseApi :NeteaseApiService by lazy {
        NetEaseRetrofitClient.instance.create(NeteaseApiService::class.java, Constant.BASE_NETEASE_URL)
    }
}