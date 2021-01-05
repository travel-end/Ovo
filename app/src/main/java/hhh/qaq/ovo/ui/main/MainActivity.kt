package hhh.qaq.ovo.ui.main

import android.content.Intent
import android.view.MenuItem
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import hhh.qaq.ovo.R
import hhh.qaq.ovo.base.BaseViewModelActivity
import hhh.qaq.ovo.delegate.NavigationManager
import hhh.qaq.ovo.utils.getFragment
import hhh.qaq.ovo.viewmodel.MainActViewModel

class MainActivity :
    BaseViewModelActivity<MainActViewModel>(R.layout.activity_main, MainActViewModel::class.java),
    NavigationView.OnNavigationItemSelectedListener {
    private lateinit var mDrawer: DrawerLayout
    private lateinit var mNavigationView: NavigationView
    private val mNavigationManager = NavigationManager(this)
    override fun initView() {
        super.initView()
        mDrawer = findViewById(R.id.main_drawer_layout)
        mNavigationView = findViewById(R.id.main_navigation_view)
        mNavigationView.setNavigationItemSelectedListener(this)
        mNavigationView.itemIconTintList = null
        mNavigationView.getHeaderView(0).let {

        }
    }

    override fun onBackPressed() {
        when {
            mDrawer.isDrawerOpen(GravityCompat.END) -> {
                mDrawer.closeDrawers()
            }
            getFragment(MainFragment::class.java) != null -> {
                // 返回桌面而不退出app
                startActivity(Intent(Intent.ACTION_MAIN).apply {
                    flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                    addCategory(Intent.CATEGORY_HOME)
                })
            }
            else -> {
                super.onBackPressed()
            }
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        mDrawer.closeDrawers()
        return mNavigationManager.onNavigationItemSelected(item)
    }
}