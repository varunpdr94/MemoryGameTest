package com.example.phonepetest

import android.support.v7.app.AppCompatActivity
import android.os.Bundle

import android.databinding.DataBindingUtil
import android.os.CountDownTimer
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.GridLayoutManager
import com.example.phonepetest.databinding.ActivityMainBinding
import com.example.phonepetest.model.Player
import com.example.testapp.adapter.MemoryGameAdapter
import com.example.testapp.manager.DataHandler
import com.example.testapp.manager.GameConfig
import android.util.DisplayMetrics
import android.widget.Toast


class MainActivity : AppCompatActivity(), MemoryGameAdapter.GameScoreInterface {


    var binding: com.example.phonepetest.databinding.ActivityMainBinding? = null

    var timer: CountDownTimer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        binding?.executePendingBindings()
        initSetUp()
    }

    private fun initSetUp() {
        val gameConfig = GameConfig(rows = 3, col = 2)

        binding?.scoreValue?.text = Player.score.toString()


        val cellSize = calculateCellSize(gameConfig.col)

        val adapter = MemoryGameAdapter(this, DataHandler.getMemoryGameData(gameConfig), this, cellSize)


        val mLayoutManager = GridLayoutManager(this, gameConfig.col)
        binding?.recyclerView?.layoutManager = mLayoutManager
        binding?.recyclerView?.itemAnimator = DefaultItemAnimator()
        binding?.recyclerView?.adapter = adapter


        timer = object : CountDownTimer(30000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                binding?.timerValue?.text = (millisUntilFinished / 1000).toString()
            }

            override fun onFinish() {
                binding?.timerValue?.text = "Game Over"
            }
        }
    }

    override fun onImageMatch() {

        Player.score++
        binding?.scoreValue?.text = Player.score.toString()
        Toast.makeText(this, "Matched", Toast.LENGTH_SHORT).show()
    }

    private fun calculateCellSize(col: Int): Int {
        val dMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(dMetrics)
        val density = dMetrics.density
        val w = Math.round(dMetrics.widthPixels / density)
        return (w / col) - DataHandler.convertDptoPixel(10)
    }

    override fun onGameFinish() {

        Toast.makeText(this, "Game Finished..Thank You", Toast.LENGTH_LONG).show(
        )
        timer?.cancel()
        binding?.timerValue?.text = "Game Finished"
    }

    override fun startGame() {
        timer?.start()
    }


}

