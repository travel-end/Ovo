package hhh.qaq.ovo.ui.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

/**
 * @By Journey 2020/12/31
 * @Description
 */
class PlayerPagerAdapter(fm:FragmentActivity,private val mFragments:MutableList<Fragment>):FragmentStateAdapter(fm) {
    override fun getItemCount()=mFragments.size
    override fun createFragment(position: Int)=mFragments[position]
}