package com.openEyes.base.dialog

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import com.openEyes.base.viewModel.BaseViewModel

/**
 * DialogFragment 带ViewModel的基类
 */
abstract class BaseVMDialogFragment<VB : ViewBinding,VM: BaseViewModel>: BaseDialogFragment<VB>() {

    /*ViewModel对象*/
    protected var viewModel:VM?=null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        addLifeCycleObserver()
        viewModel = configViewModel()
        observer()
        dataObtain()
    }

    /**
     * 添加LifeCycle对象
     */
    abstract fun addLifeCycleObserver()

    /**
     * 配置ViewModel的方法
     */
    abstract fun configViewModel():VM

    /**
     * 监听的方法
     */
    abstract fun observer()


    /**
     * 数据获取的方法
     */
    abstract fun dataObtain()



    /**
     * 通过activity获取viewModel，跟随activity生命周期
     */
    protected fun <VM: BaseViewModel> getDialogFragmentViewModel(clazz: Class<VM>): VM =
        ViewModelProvider(this).get(clazz)

    override fun onDestroyView() {
        super.onDestroyView()
        viewModel = null
    }
}