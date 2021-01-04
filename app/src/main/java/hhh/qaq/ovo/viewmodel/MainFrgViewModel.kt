package hhh.qaq.ovo.viewmodel

import android.app.Application
import android.os.Bundle
import androidx.databinding.ObservableField
import hhh.qaq.ovo.R
import hhh.qaq.ovo.base.BaseResViewModel
import hhh.qaq.ovo.constant.Constant
import hhh.qaq.ovo.repository.MainFrgRepository
import hhh.qaq.ovo.utils.getResString
import hhh.qaq.ovo.utils.log

/**
 * @By Journey 2020/12/25
 * @Description
 */
class MainFrgViewModel(app:Application):BaseResViewModel<MainFrgRepository>(app,MainFrgRepository()) {
    var mLocalMusicNum = ObservableField(R.string.song_num.getResString(0))
    var mHistoryMusicNum = ObservableField(R.string.song_num.getResString(0))
    var mCollectedMusicNum = ObservableField(R.string.song_num.getResString(0))
    var mDownloadedMusicNum = ObservableField(R.string.song_num.getResString(0))
    fun gotoSearch() {
        nav(R.id.act_main_to_search_main)
    }

    fun gotoLocalMusics() {
        nav(R.id.act_main_to_normal_music_fragment, Bundle().apply { putString(Constant.KEY_MUSIC_TYPE,Constant.PLAYLIST_LOCAL_ID) })
    }

    fun gotoHistoryMusics() {
        nav(R.id.act_main_to_normal_music_fragment, Bundle().apply { putString(Constant.KEY_MUSIC_TYPE,Constant.PLAYLIST_HISTORY_ID) })
    }

    override fun onBindViewModel() {
        super.onBindViewModel()
        loadSongs()
    }

    private fun loadSongs() {
        request {
            val localMusics = repository.getLocalMusics(getApplication())
            val collectedMusics = repository.getCollectedMusics()
            val historyMusics = repository.getHistoryMusics()
            "historyMusics:${historyMusics.size}".log()
            val downloadedMusics = repository.getDownloadedMusics()
            mLocalMusicNum.set(R.string.song_num.getResString(localMusics.size))
            mCollectedMusicNum.set(R.string.song_num.getResString(collectedMusics.size))
            mHistoryMusicNum.set(R.string.song_num.getResString(historyMusics.size))
            mDownloadedMusicNum.set(R.string.song_num.getResString(downloadedMusics.size))
        }
    }
}