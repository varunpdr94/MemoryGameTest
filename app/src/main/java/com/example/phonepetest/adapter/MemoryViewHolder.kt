package com.example.testapp.adapter

import android.databinding.ViewDataBinding
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView

class MemoryViewHolder(val binding: ViewDataBinding) : RecyclerView.ViewHolder(binding.root) {

    var thumbnail: ImageView? = null
}