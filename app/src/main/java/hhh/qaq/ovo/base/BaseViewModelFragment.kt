package hhh.qaq.ovo.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import hhh.qaq.ovo.utils.log

/**
 * @By Journey 2020/12/28
 * @Description
 */
open class BaseViewModelFragment<VM:BaseViewModel>(@LayoutRes private val layoutResId:Int,private val clazz:Class<VM>):BaseFragment() {
    protected lateinit var mViewModel:VM
    protected lateinit var mRootView:View
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mViewModel = ViewModelProvider(this)[clazz]
        val binding :ViewDataBinding= DataBindingUtil.inflate(inflater,layoutResId,container,false)
        binding.lifecycleOwner = this
        binding.setVariable(mViewModel.id(),mViewModel)
        binding.executePendingBindings()
        mRootView = binding.root
        return mRootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initData()
        onAction()
        mViewModel.setFragment(this)
        mViewModel.onBindViewModel()
    }

    open fun initView() {}

    open fun initData() {}

    open fun onAction() {}

}