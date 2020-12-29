package hhh.qaq.ovo.ui.rv

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import hhh.qaq.ovo.listener.VariableId

/**
 * @By Journey 2020/12/29
 * @Description
 */
class RvViewHolder(private val binding:ViewDataBinding):RecyclerView.ViewHolder(binding.root) {

    companion object{
        fun create(@LayoutRes layoutResId:Int,parent:ViewGroup):RvViewHolder =
            RvViewHolder(DataBindingUtil.inflate<ViewDataBinding>(LayoutInflater.from(parent.context),layoutResId,parent,false))
    }

    fun <T:VariableId> bind(item:T) {
        binding.setVariable(item.id(),item)
        binding.executePendingBindings()
    }
}