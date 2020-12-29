package hhh.qaq.ovo.databinding

import android.util.Log
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import hhh.qaq.ovo.ui.adapter.FlowTagAdapter
import hhh.qaq.ovo.utils.setDiffColor
import hhh.qaq.ovo.widget.RippleView
import hhh.qaq.ovo.widget.flowlayout.TagFlowLayout

/**
 * @By Journey 2020/12/28
 * @Description
 */


@BindingAdapter("setTagLayoutAdapter")
fun setTagLayoutAdapter(tagLayout:TagFlowLayout,tagAdapter: FlowTagAdapter){
    Log.e("JG","tagLayout:$tagLayout,tagAdapter:$tagAdapter")
    if (tagLayout.adapter != tagAdapter) {
        tagLayout.adapter = tagAdapter
    }
}

@BindingAdapter("setTagClickListener")
fun setTagClickListener(tagLayout:TagFlowLayout,tagClickListener: TagFlowLayout.OnTagClickListener?){
    if (tagClickListener == null) return
    tagLayout.setOnTagClickListener(tagClickListener)
}

@BindingAdapter(value = ["setLoadMoreListener"],requireAll = false)
fun setLoadMoreListener(rv:RecyclerView,listener:(()->Unit)?) {
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
fun setRippleViewCompleteListener(rippleView: RippleView,listener:RippleView.OnRippleCompleteListener) {
    rippleView.setOnRippleCompleteListener(listener)
}

@BindingAdapter("setDiff")
fun setDiffColor(tv:TextView,diff:DiffViewModel) {
    tv.setDiffColor(diff.appointStr.get(),diff.originalStr.get())
}