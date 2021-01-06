package hhh.qaq.ovo.repository

import android.content.Context
import hhh.qaq.ovo.data.db.SongOperator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext

/**
 * @By Journey 2020/12/25
 * @Description
 */
class MainFrgRepository:NetRepository() {
    suspend fun getLocalMusics(context: Context) = withContext(Dispatchers.IO){
        async {
            SongOperator.getLocalMusic(context)
        }
    }.await()

    suspend fun getHistoryMusics() = withContext(Dispatchers.IO){
        async {
            SongOperator.getHistoryMusic()
        }
    }.await()

    suspend fun getCollectedMusics() = withContext(Dispatchers.IO){
        async {
            SongOperator.getCollectedMusic()
        }
    }.await()

    suspend fun getDownloadedMusics() = withContext(Dispatchers.IO) {
        async {
            SongOperator.getDownloadedMusics()
        }
    }.await()
}