package com.openEyes.base.ui

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import com.openEyes.base.viewModel.BaseViewModel

/**
 * 需要创建Jetpack-ViewModel的BaseActivity基类
 */
abstract class BaseVMActivity<VB : ViewBinding,VM: BaseViewModel> : BaseActivity<VB>() {
    /*ViewModel对象*/
    protected lateinit var viewModel: VM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addLifeCycleObserver()
        configViewModel()
        observe()
        dataObtain()
    }

    /**
     * 添加LifeCycle对象
     */
    open fun addLifeCycleObserver(){}

    /**
     * 配置ViewModel的方法
     */
    abstract fun configViewModel()

    /**
     * 注册观察者
     */
    abstract fun observe()

    /**
     * 数据获取 网络/数据库
     */
    open fun dataObtain(){}


    /**
     * 通过activity获取viewModel，跟随activity生命周期
     */
    protected fun <VM: BaseViewModel> getActivityViewModel(clazz: Class<VM>): VM =
        ViewModelProvider(this).get(clazz)

}

