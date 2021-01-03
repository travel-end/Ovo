package hhh.qaq.ovo.data.db

import hhh.qaq.ovo.constant.Constant
import hhh.qaq.ovo.model.Music
import hhh.qaq.ovo.model.MusicToPlaylist
import org.litepal.LitePal

object DaoLitePal {

    fun getMusicList(playId:String,order:String=""):MutableList<Music> {
        val musiclist = mutableListOf<Music>()
        when(playId) {
            Constant.PLAYLIST_LOVE_ID->{
                val data = LitePal.where("isLove = ? ", "1").find(Music::class.java)
                musiclist.addAll(data)
            }
            Constant.PLAYLIST_LOCAL_ID->{
                val data = LitePal.where("isOnline = ? ", "0").find(Music::class.java)
                musiclist.addAll(data)
            }
            else->{
                val data = LitePal.where("pid = ?", playId).order(order).find(MusicToPlaylist::class.java)
                for (it in data) {
                    val musicList = LitePal.where("mid = ?", it.mid).find(Music::class.java)
                    musicList.addAll(musicList)
                }
            }
        }
        return musiclist
    }

    fun saveOrUpdateMusic(music: Music, isAsync: Boolean = false) {
        if (isAsync) {
            music.saveOrUpdateAsync("mid = ?", music.mid)
        } else {
            music.saveOrUpdate("mid = ?", music.mid)
        }
    }

    /**
     * 添加歌曲到歌单
     */
    fun addToPlayList(music: Music, pid: String): Boolean {
        saveOrUpdateMusic(music)
        val count = LitePal.where("mid = ? and pid = ?", music.mid, pid)
            .count(MusicToPlaylist::class.java)
        return if (count == 0) {
            val mtp = MusicToPlaylist()
            mtp.mid = music.mid
            mtp.pid = pid
            mtp.total = 1
            mtp.createDate = System.currentTimeMillis()
            mtp.updateDate = System.currentTimeMillis()
            mtp.save()
        } else {
            val mtp = MusicToPlaylist()
            mtp.total++
            mtp.updateDate = System.currentTimeMillis()
            mtp.saveOrUpdate("mid = ? and pid =?", music.mid, pid)
        }
    }

    fun getMusicInfo(mid: String): Music? {
        return LitePal.where("mid = ? ", mid).findFirst(Music::class.java)
    }

    /**
     * 清除歌单歌曲
     */
    fun clearPlayList(pid: String): Int {
        return LitePal.deleteAll(MusicToPlaylist::class.java, "pid=?", pid)
    }
}