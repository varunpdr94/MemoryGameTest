package com.example.testapp.adapter

import android.content.Context
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.phonepetest.R
import com.example.phonepetest.databinding.MemoryCardItemBinding
import com.example.testapp.model.MemoryModel
import android.databinding.adapters.TextViewBindingAdapter.setText
import android.os.CountDownTimer
import android.util.Log


class MemoryGameAdapter(private val mContext: Context, val modelList: List<MemoryModel>, val listener: GameScoreInterface, val cellSize: Int) : RecyclerView.Adapter<MemoryViewHolder>() {


    val memorySet: HashSet<Int> = HashSet<Int>()


    private var lastOpenedPosition = -1
    private var openCardCount = 0

    private var firstClick = false;

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MemoryViewHolder {

        val layoutInflater = LayoutInflater.from(parent.context)

        val binding = DataBindingUtil.inflate<ViewDataBinding>(
                layoutInflater, R.layout.memory_card_item, parent, false)

        binding.root.layoutParams.width = cellSize
        binding.root.layoutParams.height = cellSize

        return MemoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MemoryViewHolder, position: Int) {
        val model = modelList!![position]
        // loading album cover using Glide library
        val binding: com.example.phonepetest.databinding.MemoryCardItemBinding = holder.binding as MemoryCardItemBinding
        binding.imageView.setImageResource(model.id!!)
        binding.root.post {
            if (!model.isVisible) {
                binding.bannerView.visibility == View.VISIBLE
            } else {
                binding.bannerView.visibility == View.GONE
            }
            binding.executePendingBindings()
        }
        if (!binding.bannerView.hasOnClickListeners()) {
            binding.bannerView.setOnClickListener {
                if (lastOpenedPosition != -1) {
                    if (model.id == modelList.get(lastOpenedPosition).id && position != lastOpenedPosition) {
                        openCardCount += 2
                        if (openCardCount == modelList.size) {
                            listener.onGameFinish()
                        } else {
                            lastOpenedPosition = -1
                            Log.d("last position", lastOpenedPosition.toString())
                            binding.bannerView.visibility = View.GONE
                            listener.onImageMatch()
                        }

                    } else {
                        closeUnMatchItems(position, lastOpenedPosition)
                    }
                } else {
                    if (!firstClick) {
                        listener?.startGame()
                        firstClick = true
                    }
                    lastOpenedPosition = position
                    Log.d("last position", lastOpenedPosition.toString())
                    binding.bannerView.visibility = View.GONE
                }
            }
        }
    }

    private fun closeUnMatchItems(position: Int, lastPost: Int) {
        modelList.get(position).isVisible = false
        modelList.get(lastPost).isVisible = false
        lastOpenedPosition = -1;
        Log.d("pos", position.toString() + "," + lastPost.toString())
        Log.d("last position", lastOpenedPosition.toString())
        notifyDataSetChanged()
    }


    override fun getItemCount(): Int {
        return modelList!!.size
    }


    interface GameScoreInterface {
        fun onImageMatch()
        fun onGameFinish()
        fun startGame()
    }
}

