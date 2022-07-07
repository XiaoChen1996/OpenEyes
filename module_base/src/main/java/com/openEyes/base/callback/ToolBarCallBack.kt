package com.openEyes.base.callback

/**
 * BaseActivity中的ToolBar的回调方法类
 */
abstract class ToolBarCallBack {
    /**
     * 左侧返回按钮点击
     */
    abstract fun activityBack()

    /**
     * 右侧按钮点击
     */
    open fun rightImageClickEvent() {}
}