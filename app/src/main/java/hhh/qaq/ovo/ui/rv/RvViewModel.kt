package hhh.qaq.ovo.ui.rv

import android.app.Application
import androidx.databinding.ObservableField
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

/**
 * @By Journey 2020/12/29
 * @Description
 */
open class RvViewModel(app:Application) {

    var mAdapterObservable:ObservableField<RecyclerView.Adapter<RvViewHolder>> = ObservableField()
    var mLayoutManager:RecyclerView.LayoutManager = LinearLayoutManager(app)
    var mOnLoadMoreListener = {}
}