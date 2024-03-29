package com.ditclear.paonet.helper.adapter.recyclerview

import androidx.databinding.ViewDataBinding

class BindingViewHolder<out T : ViewDataBinding>(val binding: T) : androidx.recyclerview.widget.RecyclerView.ViewHolder(binding.root)