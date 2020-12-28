package hhh.qaq.ovo.base

import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import hhh.qaq.ovo.R

/**
 * @By Journey 2020/12/28
 * @Description
 */
open class BaseVpFragment<VM:BaseResViewModel<*>>(@LayoutRes layoutResId:Int,clazz: Class<VM>):BaseViewModelFragment<VM>(layoutResId,clazz) {
    private var vpTitles: Array<String>? = null
    private var vpFragments: Array<Fragment>? = null
    private lateinit var mediator: TabLayoutMediator
    protected lateinit var viewPager2:ViewPager2
    protected lateinit var tabLayout:TabLayout
    override fun initView() {
        super.initView()
        viewPager2 = mRootView.findViewById(R.id.viewPager2)
        tabLayout = mRootView.findViewById(R.id.tabLayout)
        viewPager2.adapter = VpAdapter()
        mediator = TabLayoutMediator(tabLayout,viewPager2,
            TabLayoutMediator.TabConfigurationStrategy { tab, position ->
                if (!vpTitles.isNullOrEmpty()) {
                    tab.text = vpTitles!![position]
                }
            })
        mediator.attach()
    }
    open fun initVpTitle(title: Array<String>?) {
        this.vpTitles = title
    }

    open fun initVpFragments(fragments: Array<Fragment>?) {
        this.vpFragments = fragments
    }

    inner class VpAdapter : FragmentStateAdapter(this) {
        override fun getItemCount() = if (vpFragments.isNullOrEmpty()) 0 else vpFragments!!.size
        override fun createFragment(position: Int) = vpFragments!![position]
    }
    override fun onDestroy() {
        super.onDestroy()
        mediator.detach()
    }
}