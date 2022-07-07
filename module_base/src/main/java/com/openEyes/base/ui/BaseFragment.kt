package com.openEyes.base.ui

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import java.lang.reflect.ParameterizedType

/**
 * Base Fragment基类
 * ps:Fragment生命周期的方法中，只有onCreateView()在重写时不用写super方法，其他都需要。
 */
abstract class BaseFragment<VB: ViewBinding> :Fragment() {
    /*DataBinding或者ViewBinding对象*/
    protected lateinit var binding:VB
    /*Activity对象*/
    protected lateinit var mActivity:Activity

    /**
     * onAttach()在fragment与Activity关联之后调用。需要注意的是，初始化fragment参数可以从getArguments()获得，
     * 但是，当Fragment附加到Activity之后，就无法再调用setArguments()。所以除了在最开始时，其它时间都无法向初始化参数添加内容。
     */
    override fun onAttach(context: Context) {
        super.onAttach(context)
        mActivity = context as Activity
    }

    /**
     * 系统创建Fragment时调用，作用：实例化一些变量，例如用户暂停、停止的时候想要保持的数据。
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    /**
     * 在这个fragment构造它的用户接口视图(即布局)时调用
     */
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater,getLayoutId(),container,false)
        return binding.root
    }

    /**
     * 在 onCreateView(LayoutInflater, ViewGroup, Bundle) 返回后立即调用
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dataHandle()
        viewHandle()
    }


    /**
     * 获取layout id
     */
    abstract fun getLayoutId(): Int

    /**
     * 启动Fragment时调用，此时Fragment可见
     */
    override fun onStart() {
        super.onStart()
    }

    /**
     * 当Fragment可见且可交互时调用。
     */
    override fun onResume() {
        super.onResume()
    }

    /**
     * 当Fragment不可交互但可见时调用。
     */
    override fun onPause() {
        super.onPause()
    }

    /**
     * 当Fragment不可见时调用。可能情况：activity被stopped或fragment被移除，加入到回退栈。
     * 一个stopped的fragment任然是活着的，如果长时间不用也会被移除。
     */
    override fun onStop() {
        super.onStop()
    }

    /**
     * 当Fragment的UI从视图结构中移除时调用。Fragment中的布局被移除时调用，表示fragment销毁相关联的UI布局，
     * 清除所有与视图相关的资源。（这一步骤只是移除视图，并没有销毁且没有脱离activity）
     */
    override fun onDestroyView() {
        super.onDestroyView()
    }

    /**
     * 销毁Fragment时调用。
     */
    override fun onDestroy() {
        super.onDestroy()
    }

    /**
     * 当Fragment和Activity解除关联时调用。
     */
    override fun onDetach() {
        super.onDetach()
    }

    /**
     * 在此方法中执行数据的初始化等操作
     */
    abstract fun dataHandle()

    /**
     * 在此方法中执行视图的初始化等操作
     */
    abstract fun viewHandle()

    @Suppress("UNCHECKED_CAST")
    private fun getViewBindingForFragment(layoutInflater: LayoutInflater, container: ViewGroup?): VB {
        val type = javaClass.genericSuperclass as ParameterizedType
        val aClass = type.actualTypeArguments[0] as Class<*>
        val method = aClass.getDeclaredMethod("inflate", LayoutInflater::class.java)
        return method.invoke(null, layoutInflater) as VB
    }
}