package hhh.qaq.ovo.repository

import android.content.Context
import hhh.qaq.ovo.data.db.SongOperator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MusicListRepository :NetRepository(){
    suspend fun getLocalMusics(context: Context) = withContext(Dispatchers.Default){
        SongOperator.getLocalMusic(context)
    }
}