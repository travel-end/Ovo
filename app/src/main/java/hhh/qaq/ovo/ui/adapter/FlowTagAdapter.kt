package hhh.qaq.ovo.ui.adapter

import android.view.View
import android.widget.TextView
import hhh.qaq.ovo.R
import hhh.qaq.ovo.model.HotItem
import hhh.qaq.ovo.utils.inflate
import hhh.qaq.ovo.widget.flowlayout.FlowLayout
import hhh.qaq.ovo.widget.flowlayout.TagAdapter

/**
 * @By Journey 2020/12/29
 * @Description
 */
class FlowTagAdapter(private val list:List<HotItem>):TagAdapter<HotItem>(list) {
    override fun getView(parent: FlowLayout?, position: Int, t: HotItem?): View? {
        if (list.isEmpty()) return null
        if (parent == null) return null
        val tagView = R.layout.item_round_text.inflate(parent)
        (tagView as TextView).text = t?.first
        return tagView
    }
}