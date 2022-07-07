package com.openEyes.base.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.viewbinding.ViewBinding
import com.gyf.immersionbar.ImmersionBar
import com.gyf.immersionbar.ktx.immersionBar
import com.openEyes.base.bean.baseInfo.ToolBarInfo
import com.openEyes.base.databinding.IncludeToolbarBinding

/**
 * Base Activity基类
 *
 * @Gossiping:Activity的生命周期就是它所在进程的生命周期
 *            应用程序中,一个Activity通常就是一个单独的屏幕,它上面可以显示一些控件也可以
 *            监听并处理用户的事件做出相应。
 *            Activity之间通过Intent进行通信。在Intent的描述中，有两个最重要的部分，动作和动作对应的数据。
 *
 */
abstract class BaseActivity<VB : ViewBinding> : AppCompatActivity() {

    /*DataBinding对象*/
    protected lateinit var binding: VB

    /*Activity根布局下的FrameLayout视图对象
     在不改变原activity布局的情况下 在activity顶部覆盖一层蒙版可以使用此方法
     该方法获取的是Activity根布局下的FrameLayout使用代码*/
    private val contentRootView: ViewGroup by lazy {
        findViewById(android.R.id.content)
    }

    /*包含ToolBar的View对象*/
    private val toolBarViewBinding by lazy {
        IncludeToolbarBinding.inflate(LayoutInflater.from(this@BaseActivity))
    }

    /**
     * 当活动第一启动的时候,调用该方法,可以在此时完成活动的初始化工作
     * @param savedInstanceState:该参数可以为空(null),也可以是之前调用 onSaveInstanceState()方法保存的状态信息
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //DataBinding对象的实例化
        binding = DataBindingUtil.setContentView(this, getLayoutId())
        statusBarConfig()
        toolBarConfig()
        dataHandle()
        viewHandle()
    }

    /**
     * 获取layout id
     */
    abstract fun getLayoutId(): Int

    /**
     * 状态栏样式配置
     */
    open fun statusBarConfig(isDark: Boolean = true) = immersionBar {
        transparentStatusBar()  //透明状态栏，不写默认透明色
        statusBarDarkFont(isDark)   //状态栏字体是深色，不写默认为亮色
        init()
    }

    /**
     * ToolBar对象获取
     */
    open fun toolBarObjObtain(): ToolBarInfo? = null

    /**
     * ToolBar对象的配置及事件处理
     */
    private fun toolBarConfig() {
        //只有在需要添加ToolBar的时候才添加ToolBar,否则一律不做处理
        toolBarObjObtain()?.apply {
            //状态栏高度
            val statusBarHeight = ImmersionBar.getStatusBarHeight(this@BaseActivity)

            //处理标题相关
            toolBarViewBinding.tvToolBarTitle.apply {
                text = titleContent
                setTextColor(titleTextColor)
            }

            //左侧返回键处理
            toolBarViewBinding.ivBack.apply {
                if (needBackImg) {
                    visibility = View.VISIBLE
                    setOnClickListener { toolBarCallBack?.activityBack() ?: finish() }
                }
            }

            //右侧按钮处理
            toolBarViewBinding.ivRightImg.apply {
                if (rightImg != -1) {
                    setImageResource(rightImg)
                    setOnClickListener { toolBarCallBack?.rightImageClickEvent() }
                }
            }

            //处理布局的位置关系
            toolBarViewBinding.root.apply {
                //设置ToolBar布局距离顶部的距离(状态栏)
                (layoutParams as FrameLayout.LayoutParams).topMargin = statusBarHeight
                //添加ToolBar对象
                contentRootView.addView(this)
                //设置内容布局视图距离顶部的距离(状态栏 + 导航栏)
                binding.root.layoutParams = FrameLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                ).also {
                    it.topMargin = statusBarHeight + height
                }
            }

        }

    }

    /**
     * 在此方法中执行数据的初始化等操作
     */
    open fun dataHandle() {}

    /**
     * 视图处理的方法
     */
    open fun viewHandle() {}


    /**
     * 当处于停止状态的活动需要再次展现给用户的时候,触发该方法
     */
    override fun onRestart() {
        super.onRestart()
    }

    /**
     * 该方法的触发表示所属活动将被展现给用户
     */
    override fun onStart() {
        super.onStart()
    }

    /**
     * 当一个活动和用户发生交互的时候,触发该方法
     */
    override fun onResume() {
        super.onResume()
    }

    /**
     * 当一个正在前台运行的活动因为其他的活动需要前台运行而转入后台运行的时候,触发该方法。
     * 这时候需要将该活动的状态持久化,比如正在编辑的数据库记录等
     */
    override fun onPause() {
        super.onPause()
    }

    /**
     * 当一个活动不再需要展示给用户的时候,触发该方法。
     * 如果内存紧张,系统会直接结束这个活动,而不会触发 onStop 方法。
     * 所以保存状态信息是应该在onPause时做,而不是在onStop时做。
     * 活动如果没有在前台运行,都将被停止或者Linux管理进程为了给新的活动预留足够的存储空间而随时结束这些活动
     * 因此对于开发者来说,在设计应用程序的时候,必须牢记这一原则
     * 在一些情况下,onPause方法或许是活动触发的最后的方法,因此开发者需要在这个时候保存需要保存的信息
     */
    override fun onStop() {
        super.onStop()
    }

    /**
     * 当活动销毁的时候,触发该方法
     * 和 onStop 方法一样,如果内存紧张,系统会直接结束这个活动而不会触发该方法
     */
    override fun onDestroy() {
        //清除所有子控件
        contentRootView.removeAllViews()
        super.onDestroy()
    }
}