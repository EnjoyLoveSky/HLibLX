package com.ditclear.paonet.helper.adapter.recyclerview

import android.view.View
import androidx.databinding.ViewDataBinding


/**
 */
interface  ItemClickPresenter<in Any> {
    fun onItemClick(v: View?=null, item:Any)
}

interface ItemDecorator{
    fun decorator(holder: BindingViewHolder<ViewDataBinding>?, position: Int, viewType: Int)
}

interface ItemAnimator{

    fun scrollUpAnim(v:View)

    fun scrollDownAnim(v: View)
}