package hhh.qaq.ovo.ui.search

import android.os.Bundle
import hhh.qaq.ovo.R
import hhh.qaq.ovo.base.BaseVpFragment
import hhh.qaq.ovo.constant.Constant
import hhh.qaq.ovo.utils.getResString
import hhh.qaq.ovo.viewmodel.SearchResultViewModel

/**
 * @By Journey 2020/12/29
 * @Description
 */
class SearchResultFragment:BaseVpFragment<SearchResultViewModel>(R.layout.view_tab_vp,SearchResultViewModel::class.java) {
    private var searchKey:String = ""
    override fun getBundle(bundle: Bundle) {
        super.getBundle(bundle)
        searchKey = bundle.getString(Constant.KEY_SEARCH_CONTENT,"")
    }
    override fun initView() {
        super.initView()
        initVpTitle(
            arrayOf(
                R.string.single_song.getResString(),
                R.string.album.getResString()
            )
        )
        initVpFragments(
            arrayOf(
                SearchMusicFragment.newInstance(searchKey),
                SearchAlbumFragment.newInstance(searchKey)
            )
        )
    }

}