package com.openEyes.base.bean.baseInfo

import android.content.Context
import androidx.core.content.ContextCompat
import com.openEyes.base.R
import com.openEyes.base.callback.ToolBarCallBack

/**
 * 导航栏标题实体类
 * @param context:Context上下文对象
 * @param titleContent:标题文本内容
 * @param titleTextColor:标题颜色
 * @param needBackImg:是否需要展示返回键按钮
 * @param toolBarCallBack:ToolBar的回调对象
 */
data class ToolBarInfo(
    val context: Context,
    val titleContent: String = "",
    val titleTextColor: Int = ContextCompat.getColor(context, R.color._292929),
    val needBackImg: Boolean = false,
    val rightImg:Int = -1,
    val toolBarCallBack: ToolBarCallBack?=null
)
