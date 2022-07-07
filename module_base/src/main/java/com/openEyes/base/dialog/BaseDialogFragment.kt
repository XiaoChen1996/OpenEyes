package com.openEyes.base.dialog

import android.os.Bundle
import android.view.*
import androidx.annotation.StyleRes
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import androidx.viewbinding.ViewBinding
import com.openEyes.base.R

/**
 * DialogFragment基类
 */
abstract class BaseDialogFragment<VB : ViewBinding> : DialogFragment() {

    /*ViewBinding对象*/
    protected var binding: VB? = null

    //弹窗位置
    private var dialogGravity = Gravity.CENTER

    //点击弹窗周围空白区域能否关闭弹窗,默认为false
    private var touchOutsideDialogCancel = false

    //弹窗背景透明度
    private var dialogTransparency = 0.5f

    //弹窗动画
    @StyleRes
    private var dialogAnimation: Int? = null

    //弹窗宽度
    private var dialogWidth = ViewGroup.LayoutParams.MATCH_PARENT

    //弹窗高度
    private var dialogHeight = ViewGroup.LayoutParams.WRAP_CONTENT


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.DialogCommonStyle)
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater,getLayoutId(),container,false)
        viewHandle()
        return binding?.root ?: super.onCreateView(inflater, container, savedInstanceState)
    }


    /**
     * 获取layout id
     */
    abstract fun getLayoutId(): Int

    override fun onStart() {
        super.onStart()
        dialog?.setCanceledOnTouchOutside(touchOutsideDialogCancel)
        dialog?.window?.apply {
            val attrs = attributes?.also {
                it.width = dialogWidth
                it.height = dialogHeight
            }
            attributes = attrs
            setGravity(dialogGravity)
            setDimAmount(dialogTransparency)
            dialogAnimation?.let {
                setWindowAnimations(it) }
        }
    }

    override fun onResume() {
        super.onResume()
    }

    /**
     * 处理视图展示的方法
     */
    abstract fun viewHandle()

    /**
     * 设置弹窗相关配置
     */
    protected fun setDialogConfig(
        width: Int = ViewGroup.LayoutParams.WRAP_CONTENT,
        height: Int = ViewGroup.LayoutParams.WRAP_CONTENT,
        gravity: Int = Gravity.CENTER,
        cancel:Boolean = false,
        transparency: Float = 0.5f,
        @StyleRes animation: Int?=null
    ) {
        dialogWidth = width
        dialogHeight = height
        dialogGravity = gravity
        touchOutsideDialogCancel = cancel
        dialogTransparency = transparency
        dialogAnimation = animation
    }

    override fun show(manager: FragmentManager, tag: String?) {
        try {
            //在每个add事务前增加一个remove事务，防止连续的add  commitAllowingStateLoss:允许状态缺失
            manager.beginTransaction().remove(this).commitAllowingStateLoss()
            super.show(manager, tag)
        } catch (e: Exception) {
            //同一实例使用不同的tag会异常，这里捕获一下
            e.printStackTrace()
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}