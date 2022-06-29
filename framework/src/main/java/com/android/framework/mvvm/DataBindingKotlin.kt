package com.android.framework.mvvm

import `in`.srain.cube.views.ptr.PtrDefaultHandler
import `in`.srain.cube.views.ptr.PtrFrameLayout
import android.graphics.Outline
import android.view.View
import android.view.ViewOutlineProvider
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.framework.R
import com.android.framework.uitls.PtrFrameUtils
import com.android.framework.uitls.ViewTools
import com.bumptech.glide.Glide

/**
 * @author 陈自强
 * @date 2021/4/7/0007
 * Description:
 */
object DataBindingKotlin {

    /**
     * 1.加载图片,无需手动调用此方法
     * 2.使用@BindingAdapter注解设置自定义属性的名称，imageUrl就是属性的名称，
     * 当ImageView中使用imageUrl属性时，会自动调用loadImage方法，
     *
     * @param imageView ImageView
     * @param url       图片地址
     */
    @JvmStatic
    @BindingAdapter("loadImageUrl")
    fun loadImage(imageView: ImageView, url: String?) {
        loadImage(imageView, url)
    }

    @JvmStatic
    @BindingAdapter("loadImageRes")
    fun loadImage(imageView: ImageView, resId: Int?) {
        loadImage(imageView, resId)
    }

    @JvmStatic
    private fun loadImage(imageView: ImageView, res: Any) {
        Glide.get(imageView.context).clearMemory()
        Glide.with(imageView.context).load(res)
            .load(R.drawable.icon_loading)
            .error(R.mipmap.icon_default_error)
            .into(imageView)
    }

    /**
     * 任意View裁剪圆角
     *
     * @param view 要裁剪的View
     * @param roundDP 圆角度数，单位为DP，
     */
    @JvmStatic
    @BindingAdapter("cutViewRound")
    fun cutViewRound(view: View, roundDP: Int) {
        view.clipToOutline = true
        view.outlineProvider = object : ViewOutlineProvider() {
            override fun getOutline(view: View, outline: Outline) {
                outline.setRoundRect(
                    0,
                    0,
                    view.width,
                    view.height,
                    ViewTools.dp2px(roundDP.toFloat()).toFloat()
                )
            }
        }
    }


    /**
     * RecyclerView 设置Adapter
     * @param linearAdapter 设置adapter同时设置LinearLayoutManager
     * @param orientation 设置方向
     */
    @JvmStatic
    private fun setLinearAdapter(
        view: RecyclerView,
        linearAdapter: RecyclerView.Adapter<*>,
        orientation: Int = RecyclerView.VERTICAL
    ) {
        val manager = LinearLayoutManager(view.context)
        manager.orientation = orientation
        view.layoutManager = manager
        view.adapter = linearAdapter
    }

    @JvmStatic
    @BindingAdapter(value = ["linearAdapter", "orientation"])
    fun setRecyclerLinearAdapter(
        view: RecyclerView, linearAdapter: RecyclerView.Adapter<*>, orientation: Int
    ) {
        setLinearAdapter(view, linearAdapter, orientation)
    }

    @JvmStatic
    @BindingAdapter(value = ["linearAdapter"])
    fun setRecyclerLinearAdapter(view: RecyclerView, linearAdapter: RecyclerView.Adapter<*>) {
        setLinearAdapter(view, linearAdapter)
    }

    /**
     * RecyclerView 设置Adapter
     * @param gridAdapter 设置adapter同时设置LinearLayoutManager
     * @param spanCount 设置格数
     * @param orientation 设置方向
     */
    @JvmStatic
    private fun seGridAdapter(
        view: RecyclerView,
        gridAdapter: RecyclerView.Adapter<*>,
        spanCount: Int,
        orientation: Int = RecyclerView.VERTICAL,
        reverseLayout: Boolean = false
    ) {
        val manager = GridLayoutManager(view.context, spanCount)
        manager.orientation = orientation
        manager.reverseLayout = reverseLayout
        view.layoutManager = manager
        view.adapter = gridAdapter
    }

    @JvmStatic
    @BindingAdapter(
        value = ["gridAdapter", "spanCount", "orientation", "reverseLayout"]
    )
    fun setRecyclerGridAdapter(
        view: RecyclerView,
        gridAdapter: RecyclerView.Adapter<*>,
        spanCount: Int,
        orientation: Int,
        reverseLayout: Boolean
    ) {
        seGridAdapter(view, gridAdapter, spanCount, orientation, reverseLayout)
    }

    @JvmStatic
    @BindingAdapter(value = ["gridAdapter", "spanCount", "orientation"])
    fun setRecyclerGridAdapter(
        view: RecyclerView,
        gridAdapter: RecyclerView.Adapter<*>,
        spanCount: Int,
        orientation: Int,
    ) {
        seGridAdapter(view, gridAdapter, spanCount, orientation)
    }

    @JvmStatic
    @BindingAdapter(value = ["gridAdapter", "spanCount"])
    fun setRecyclerGridAdapter(
        view: RecyclerView, gridAdapter: RecyclerView.Adapter<*>, spanCount: Int
    ) {
        seGridAdapter(view, gridAdapter, spanCount)
    }


    /**
     * 上拉下拉设置，data binding 暂时没有用
     *
     * @param ptrLayout
     * @param ptrType       0为上拉加载和下拉刷新功能都有，1为只有上拉加载功能，2为只有下拉刷新功能
     */
    @JvmStatic
    @BindingAdapter(value = ["ptrType","ptrHandler"])
    fun setPtrFrameType(ptrLayout: PtrFrameLayout, ptrType: Int, ptrHandler: PtrDefaultHandler) {
        if (ptrType == PtrFrameUtils.MODEL_LOAD_REFRESH || ptrType == PtrFrameUtils.MODEL_LOAD) {
            PtrFrameUtils.setDefaultFooter(ptrLayout, ptrLayout.context)
        }
        if (ptrType == PtrFrameUtils.MODEL_LOAD_REFRESH || ptrType == PtrFrameUtils.MODEL_REFRESH) {
            PtrFrameUtils.setDefaultHeader(ptrLayout, ptrLayout.context)
        }
        ptrLayout.setPtrHandler(ptrHandler)
    }
}