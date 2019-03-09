package com.example.testapp.manager

import com.example.phonepetest.R
import com.example.testapp.model.MemoryModel
import java.util.*
import android.util.DisplayMetrics
import android.content.res.Resources


public class DataHandler {

    companion object {


        fun getMemoryGameData(gameConfig: GameConfig): ArrayList<MemoryModel> {


            val modelList = ArrayList<MemoryModel>()

            modelList.add(MemoryModel(R.drawable.img_1, true))

            modelList.add(MemoryModel(R.drawable.img_2, false))

            modelList.add(MemoryModel(R.drawable.img_3, false))

            modelList.add(MemoryModel(R.drawable.img_4, false))

            modelList.add(MemoryModel(R.drawable.img_5, false))

            modelList.add(MemoryModel(R.drawable.img_3, false))

            modelList.add(MemoryModel(R.drawable.img_2, false))

            modelList.add(MemoryModel(R.drawable.img_1, false))

            modelList.add(MemoryModel(R.drawable.img_3, false))

            return modelList
        }

        fun convertDptoPixel(dp:Int):Int{
            val metrics = Resources.getSystem().getDisplayMetrics()
            val px = dp * (metrics.densityDpi / 160f)
            return Math.round(px)
        }
    }


}
