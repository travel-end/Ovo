package hhh.qaq.ovo.repository

import android.content.Context
import hhh.qaq.ovo.constant.Constant
import hhh.qaq.ovo.data.db.SongOperator
import hhh.qaq.ovo.model.Music
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MusicListRepository :NetRepository(){
    private suspend fun getLocalMusics(context: Context) = withContext(Dispatchers.Default){
        SongOperator.getLocalMusic(context)
    }
    private suspend fun getHistoryMusics() = withContext(Dispatchers.Default){
        SongOperator.getHistoryMusic()
    }

    suspend fun getNormalMusics(context: Context,musicType:String?) :MutableList<Music>?{
        if (musicType.isNullOrBlank()) return null
        return when(musicType) {
            Constant.PLAYLIST_LOCAL_ID->getLocalMusics(context)
            Constant.PLAYLIST_HISTORY_ID->getHistoryMusics()
            else->return null
        }
    }
}