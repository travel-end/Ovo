package hhh.qaq.ovo.data.db

import android.content.Context
import android.database.Cursor
import android.provider.MediaStore
import android.text.TextUtils
import hhh.qaq.ovo.constant.Constant
import hhh.qaq.ovo.model.Music
import hhh.qaq.ovo.model.Playlist
import hhh.qaq.ovo.model.TasksManagerModel
import hhh.qaq.ovo.utils.execute
import hhh.qaq.ovo.utils.getLocalSongCover
import hhh.qaq.ovo.utils.log
import org.litepal.LitePal
import java.io.File

object SongOperator {
    private fun getLocalSongsFromDb():MutableList<Music> {
        return DaoLitePal.getMusicList(Constant.PLAYLIST_LOCAL_ID)
    }

    // 获取本地音乐
    fun getLocalMusic(context: Context, isReload: Boolean = true): MutableList<Music> {
        val data = getLocalSongsFromDb()
        if (data.size == 0 || isReload) {
            data.clear()
            val musicLists = getAllLocalSongs(context)
            musicLists.forEach {
                data.add(it)
            }
        }
        return data
    }

    // 获取播放历史音乐
    fun getHistoryPlayList():Playlist {
        return DaoLitePal.getPlayList(Constant.PLAYLIST_HISTORY_ID)
    }
    // 获取播放历史音乐
    fun getHistoryMusic():MutableList<Music> {
        return DaoLitePal.getMusicList(Constant.PLAYLIST_HISTORY_ID,"updateDate desc")
    }


    // 获取收藏的歌曲
    fun getCollectedMusics():MutableList<Music> {
        return DaoLitePal.getMusicList(Constant.PLAYLIST_LOVE_ID)
    }

    // 获取下载的歌曲
    fun getDownloadedMusics():MutableList<Music> {
        val musicList = mutableListOf<Music>()
        val data = LitePal.where("finish = 1").find(TasksManagerModel::class.java)
        data.forEach {
            val music = it.mid?.let { it1 ->
                try {
                    DaoLitePal.getMusicInfo(it1)
                } catch (e: Throwable) {
                    null
                }
            }
            music?.let { it1 -> musicList.add(it1) }
        }
        return musicList
    }

    // 添加歌曲到历史记录列表
    fun addToHistoryMusic(music: Music?) {
        if (music==null) return
        try {
            DaoLitePal.addToPlayList(music, Constant.PLAYLIST_HISTORY_ID)
        } catch (e: Throwable) {
            e.printStackTrace()
        }
    }

    //清楚历史记录音乐
    fun clearHistoryMusic() {
        try {
            DaoLitePal.clearPlayList(Constant.PLAYLIST_HISTORY_ID)
        } catch (e: Throwable) {
            e.printStackTrace()
        }
    }
/* ******************歌单操作*****************/

    // 添加歌曲到歌单
    fun addMusicToQueue(musics:List<Music>) {
        execute {
            clearQueue()
            musics.forEach {
                DaoLitePal.addToPlayList(it,Constant.PLAYLIST_QUEUE_ID)
            }
        }
    }

    // 获取播放队列
    fun getPlayQueue():List<Music> {
        return DaoLitePal.getMusicList(Constant.PLAYLIST_QUEUE_ID)
    }

    // 清空播放列表
    private fun clearQueue() {
        try {
            DaoLitePal.clearPlayList(Constant.PLAYLIST_QUEUE_ID)
        } catch (e: Throwable) {
            e.printStackTrace()
        }
    }

    private fun getAllLocalSongs(context: Context):MutableList<Music> {
        return getSongsForMedia(context,makeSongCursor(context, null, null))
    }
    private fun makeSongCursor(context: Context, selection: String?, paramArrayOfString: Array<String>?): Cursor? {
        val songSortOrder = MediaStore.Audio.Media.DEFAULT_SORT_ORDER
        return makeSongCursor(context, selection, paramArrayOfString, songSortOrder)
    }
    private fun makeSongCursor(context: Context, selection: String?, paramArrayOfString: Array<String>?, sortOrder: String?): Cursor? {
        var selectionStatement = "duration>60000 AND is_music=1 AND title != ''"
        if (!TextUtils.isEmpty(selection)) {
            selectionStatement = "$selectionStatement AND $selection"
        }
        return context.contentResolver.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
            arrayOf("_id", "title", "artist", "album", "duration", "track", "artist_id", "album_id", MediaStore.Audio.Media.DATA, "is_music"),
            selectionStatement, paramArrayOfString, sortOrder)
    }
    private fun getSongsForMedia(context: Context, cursor: Cursor?): MutableList<Music> {
        return getSongsForMedia(context, cursor, null);
    }

    private fun getSongsForMedia(context: Context, cursor: Cursor?, folderPath: String?): MutableList<Music> {
        val results = mutableListOf<Music>()
        try {
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    val id = cursor.getLong(0)
                    val title = cursor.getString(1)
                    val artist = cursor.getString(2)
                    val album = cursor.getString(3)
                    val duration = cursor.getInt(4)
                    val trackNumber = cursor.getInt(5)
                    val artistId = cursor.getString(6)
                    val albumId = cursor.getString(7)
                    val path = cursor.getString(8)
                    val coverUri = getLocalSongCover(context, albumId)
                    val music = Music()
                    music.type = Constant.LOCAL
                    music.isOnline = false
                    music.mid = id.toString()
                    music.album = album
                    music.albumId = albumId
                    music.artist = if (artist == "<unknown>") "未知歌手" else artist
                    music.artistId = artistId
                    music.uri = path
                    coverUri?.let { music.coverUri = it }
                    music.trackNumber = trackNumber
                    music.duration = duration.toLong()
                    music.title = title
                    music.date = System.currentTimeMillis()
                    //解决获取文件夹内歌曲时，数量不对
                    if (TextUtils.isEmpty(folderPath) || (!TextUtils.isEmpty(folderPath) && File(path).parent == folderPath)) {
                        DaoLitePal.saveOrUpdateMusic(music)
                        results.add(music)
                    }
                } while (cursor.moveToNext())
            }
            cursor?.close()
        } catch (e: Throwable) {
            e.printStackTrace()
        }
        return results
    }
}