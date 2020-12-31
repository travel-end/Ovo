package hhh.qaq.ovo.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * @By Journey 2020/12/31
 * @Description
 */
class PlayerLyricRepository:NetRepository() {
    suspend fun getLyric(songId:String) = withContext(Dispatchers.IO){
        apiService.getOnlineSongLrc(songId)
    }
}