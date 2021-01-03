package hhh.qaq.ovo.repository

import hhh.qaq.ovo.base.BaseRepository
import hhh.qaq.ovo.data.ApiService
import hhh.qaq.ovo.data.RetrofitClient

/**
 * @By Journey 2020/12/25
 * @Description
 */
open class NetRepository:BaseRepository {
    private val intervalTime = 1000 * 60 * 60 * 4//

    protected val apiService by lazy {
        RetrofitClient.instance.apiService
    }
    protected val singerApiService: ApiService by lazy {
        RetrofitClient.instance.singerApiService
    }
    protected val songUrlApiService: ApiService by lazy {
        RetrofitClient.instance.songUrlApiService
    }
    protected val neteaseApiService: ApiService by lazy {
        RetrofitClient.instance.neteaseApiService
    }
    fun Long?.shouldUpdate() = (System.currentTimeMillis() - (this ?: 0L)) > intervalTime
}