package hhh.qaq.ovo.viewmodel

import android.app.Application
import androidx.databinding.ObservableField
import hhh.qaq.ovo.R
import hhh.qaq.ovo.base.BaseResViewModel
import hhh.qaq.ovo.base.BaseViewModel
import hhh.qaq.ovo.playmedia.PlayManager
import hhh.qaq.ovo.repository.PlayerLyricRepository
import hhh.qaq.ovo.utils.getResString

/**
 * @By Journey 2020/12/31
 * @Description
 */
class PlayerLyricViewModel(app:Application):BaseResViewModel<PlayerLyricRepository>(app,PlayerLyricRepository()) {
    var lyricObservable = ObservableField("")
    var lyricProgress = ObservableField<Long>(0)
    override fun onBindViewModel() {
        super.onBindViewModel()
        PlayManager.getPlayingMusic()?.let {
            val songId = it.mid?:return@let
            request {
                val lyric = repository.getLyric(songId)?.lyric
                if (lyric.isNullOrBlank()) {
                    lyricObservable.set(R.string.lrc_empty.getResString())
                } else {
                    lyricObservable.set(lyric)
                }
            }
        }
    }

    fun updateLyricProgress(progress:Long) {
        lyricProgress.set(progress)
    }
}