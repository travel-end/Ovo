package hhh.qaq.ovo.event

import hhh.qaq.ovo.constant.Constant
import hhh.qaq.ovo.model.Music
import hhh.qaq.ovo.model.Playlist

/**
 * @By Journey 2020/12/7
 * @Description
 */

//歌单改变
data class PlaylistEvent(
    var type:String?= Constant.PLAYLIST_CUSTOM_ID,
    val playList: Playlist?=null
)

data class StatusChangedEvent(
    var isPrepared:Boolean,
    var isPlaying:Boolean,
    var percent:Int = 0
)

// 歌曲变化
data class MetaChangedEvent(
    var music: Music?
)

