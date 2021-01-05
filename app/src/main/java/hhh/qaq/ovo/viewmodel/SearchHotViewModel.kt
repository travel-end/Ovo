package hhh.qaq.ovo.viewmodel

import android.app.Application
import android.os.Bundle
import android.view.View
import androidx.databinding.ObservableField
import hhh.qaq.ovo.R
import hhh.qaq.ovo.base.BaseResViewModel
import hhh.qaq.ovo.constant.Constant
import hhh.qaq.ovo.model.HotItem
import hhh.qaq.ovo.repository.SearchHotRepository
import hhh.qaq.ovo.ui.adapter.FlowTagAdapter
import hhh.qaq.ovo.ui.search.SearchHotFragment
import hhh.qaq.ovo.widget.flowlayout.TagFlowLayout

/**
 * @By Journey 2020/12/28
 * @Description
 */
class SearchHotViewModel(app:Application):BaseResViewModel<SearchHotRepository>(app,SearchHotRepository()) {
    private val tagList = arrayListOf<HotItem>()
    private val mTagAdapter = FlowTagAdapter(tagList)
    private var mFragment:SearchHotFragment?=null
    var mHotTagAdapter = ObservableField<FlowTagAdapter>(mTagAdapter)
    var mShowTagLayout= ObservableField<Boolean>(false)
    var mTagClickListener =
        TagFlowLayout.OnTagClickListener { v, position, _ ->
            val item = tagList[position]
            nav(v,R.id.hot_to_search_result_fragment, Bundle().apply { putString(Constant.KEY_SEARCH_CONTENT,item.first) })
            false
        }

    var mDeleteSearchHistoryListener = View.OnClickListener {
    }
    fun setHotFragment(f:SearchHotFragment?) {
        mFragment = f
    }

    fun navigation(event:String?) {
        nav(mFragment,R.id.hot_to_search_result_fragment, Bundle().apply { putString(Constant.KEY_SEARCH_CONTENT,event) })
    }

    override fun onBindViewModel() {
        super.onBindViewModel()
        request {
            getHotSearchTag()
            getHistorySearch()
        }
    }

    private suspend fun getHotSearchTag() {
        tagList.clear()
        val hots = repository.getHotSearchTag().result?.hots
        if (!hots.isNullOrEmpty()) {
            mShowTagLayout.set(true)
            hots.forEach {
                tagList.add(it)
            }
            mTagAdapter.setNewData(tagList)
        }
    }
    private suspend fun getHistorySearch() {
        repository.getHistorySearch()
    }
}