package hhh.qaq.ovo.databinding

import android.graphics.drawable.Drawable
import android.util.Log
import android.widget.ImageView
import android.widget.SeekBar
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import hhh.qaq.ovo.R
import hhh.qaq.ovo.playmedia.PlayManager
import hhh.qaq.ovo.ui.adapter.FlowTagAdapter
import hhh.qaq.ovo.utils.hideKeyboards
import hhh.qaq.ovo.utils.loadImg
import hhh.qaq.ovo.utils.setDiffColor
import hhh.qaq.ovo.utils.showKeyBoard
import hhh.qaq.ovo.widget.ClearEditText
import hhh.qaq.ovo.widget.PlayPauseView
import hhh.qaq.ovo.widget.RippleView
import hhh.qaq.ovo.widget.flowlayout.TagFlowLayout
import hhh.qaq.ovo.widget.lrc.LrcView

/**
 * @By Journey 2020/12/28
 * @Description
 */

@BindingAdapter(value = ["loadImgUrl"], requireAll = false)
fun loadImgUrl(iv: ImageView, url: String?) {
    if (url.isNullOrBlank()) return
    iv.loadImg(url, placeholder = R.drawable.disk, error = R.drawable.disk)
}

@BindingAdapter("setTagLayoutAdapter")
fun setTagLayoutAdapter(tagLayout: TagFlowLayout, tagAdapter: FlowTagAdapter) {
    if (tagLayout.adapter != tagAdapter) {
        tagLayout.adapter = tagAdapter
    }
}

@BindingAdapter("setTagClickListener")
fun setTagClickListener(
    tagLayout: TagFlowLayout,
    tagClickListener: TagFlowLayout.OnTagClickListener?
) {
    if (tagClickListener == null) return
    tagLayout.setOnTagClickListener(tagClickListener)
}

//@BindingAdapter("setOnSeekBarChangeListener")
//fun setOnSeekBarChangeListener(seekBar: SeekBar,listener:SeekBar.OnSeekBarChangeListener?) {
//    if (listener==null) return
//    seekBar.setOnSeekBarChangeListener(listener)
//}

@BindingAdapter(value = ["setLoadMoreListener"], requireAll = false)
fun setLoadMoreListener(rv: RecyclerView, listener: (() -> Unit)?) {
    var isToTop = false
    rv.addOnScrollListener(object : RecyclerView.OnScrollListener() {
        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            val lm = recyclerView.layoutManager
            if (lm is LinearLayoutManager) {
                val lastVisibleItemPosition = lm.findLastVisibleItemPosition()
                val totalItemCount = recyclerView.adapter?.itemCount ?: 0
                val visibleChildCount = recyclerView.childCount

                if (
                    isToTop &&
                    newState == RecyclerView.SCROLL_STATE_IDLE &&
                    totalItemCount != 0 &&
                    lastVisibleItemPosition == totalItemCount - 1 &&
                    visibleChildCount != 0
                ) {
                    listener?.invoke()
                }
            }
        }

        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            isToTop = dy > 0
        }
    })
}

@BindingAdapter("setRippleViewCompleteListener")
fun setRippleViewCompleteListener(
    rippleView: RippleView,
    listener: RippleView.OnRippleCompleteListener
) {
    rippleView.setOnRippleCompleteListener(listener)
}

@BindingAdapter("setDiffColor")
fun setDiffColor(tv: TextView, diff: DiffViewModel) {
    tv.setDiffColor(diff.appointStr.get(), diff.originalStr.get())
}

@BindingAdapter("showKeyboard")
fun showKeyboard(et: ClearEditText, showKb: Boolean) {
    if (showKb) {
        et.showKeyBoard()
    } else {
        et.context.hideKeyboards()
    }
}

@BindingAdapter("isMusicPlaying")
fun isMusicPlaying(playPauseView: PlayPauseView, isPlaying: Boolean) {
    if (isPlaying && !playPauseView.isPlaying) {
        playPauseView.play()
    } else if (!isPlaying && playPauseView.isPlaying) {
        playPauseView.pause()
    }
}

//@BindingAdapter("setPausePlayViewProgress")
//fun setPausePlayViewProgress(playPauseView: PlayPauseView,isSelected:Boolean) {
//    iv.isSelected = isSelected
//}

@BindingAdapter("setImageDrawable")
fun setImageDrawable(iv: ImageView,drawable: Drawable) {
    iv.setImageDrawable(drawable)
}

@BindingAdapter("loadLyric")
fun loadLyric(lrc:LrcView,lyric:String) {
    lrc.loadLrc(lyric)
}

@BindingAdapter("updateLyricProgress")
fun updateLyricProgress(lrc: LrcView,progress:Long) {
    if (lrc.hasLrc()) {
        lrc.updateTime(progress)
    }
}
