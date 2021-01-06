package hhh.qaq.ovo.data

import com.cyl.musicapi.BaseApiImpl
import hhh.qaq.ovo.constant.Constant
import hhh.qaq.ovo.model.Music
import hhh.qaq.ovo.utils.FileUtils
import hhh.qaq.ovo.utils.SpUtil
import hhh.qaq.ovo.utils.execute
import hhh.qaq.ovo.utils.log

/**
 * @By Journey 2020/12/30
 * @Description
 */
object MusicApi {

    fun getMusicPlayUrl(music: Music?): Music? {
        if (music == null) return null
        var quality = SpUtil.getInt(Constant.KEY_SONG_QUALITY, music.quality)
        if (music.quality != quality) {
            quality = when {
                quality >= 999000 && music.sq -> 999000
                quality >= 320000 && music.hq -> 320000
                quality >= 192000 && music.high -> 192000
                quality >= 128000 -> 128000
                else -> 128000
            }
        }
        val cachePath =
            FileUtils.getMusicCacheDir() + music.artist + " - " + music.title + "(" + quality + ")"
        val downloadPath = FileUtils.getMusicDir() + music.artist + " - " + music.title + ".mp3"
        if (FileUtils.exists(cachePath)) {
            music.uri = cachePath
            return music
        } else if (FileUtils.exists(downloadPath)) {
            music.uri = downloadPath
            return music
        }
        if (music.type == Constant.QQ) {
            var playUrl: String? = null
            val songUrl =
                "${Constant.SONG_URL_DATA_LEFT}${music.mid}${Constant.SONG_URL_DATA_RIGHT}"
            execute {
                val result =
                    QQRetrofitClient.getInstance().songUrlApiService.getSongUrl(songUrl)
                if (result.code == 0) {
                    val sipList = result.req_0?.data?.sip
                    var sip = ""
                    if (sipList != null) {
                        if (sipList.isNotEmpty()) {
                            sip = sipList[0]
                        }
                    }
                    val purlList = result.req_0?.data?.midurlinfo
                    var pUrl: String? = ""
                    if (purlList != null) {
                        if (purlList.isNotEmpty()) {
                            pUrl = purlList[0].purl
                        }
                    }
                    playUrl = "$sip$pUrl"
                }
            }
            music.uri = playUrl
            return music
        } else {
            val uri = getMusicPlayUrl(
                music.type ?: Constant.LOCAL,
                music.mid ?: "",
                quality
            )
            music.uri = uri
            return music
        }
    }

    fun getMusicPlayUrl(vendor: String, mid: String, br: Int = 128000): String? {
        var url: String? = null
        execute {
            BaseApiImpl.getSongUrl(vendor, mid, br, {
                if (it.status) {
                    url =
                        if (vendor == Constant.XIAMI) {
                            if (it.data.url.startsWith("http")) it.data.url
                            else "http:${it.data.url}"
                        } else it.data.url

                } else {
                    "----MusicApiServiceImpl---onError:获取播放地址异常${it.msg}".log()
                }
            }, {})
        }
        return url
    }
}