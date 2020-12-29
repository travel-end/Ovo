package hhh.qaq.ovo.ui.adapter

import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import hhh.qaq.ovo.ui.rv.RvViewHolder
import hhh.qaq.ovo.viewmodel.BaseItemViewModel

/**
 * @By Journey 2020/12/29
 * @Description
 */
class MusicItemAdapter<T:BaseItemViewModel>(@LayoutRes var layoutResId:Int, private val mData:MutableList<T>):RecyclerView.Adapter<RvViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RvViewHolder =RvViewHolder.create(layoutResId,parent)
    override fun getItemCount()=mData.size
    override fun onBindViewHolder(holder: RvViewHolder, position: Int) {
        holder.bind(mData[position])
    }
}